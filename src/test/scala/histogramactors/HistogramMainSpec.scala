package histogramactors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen}

class HistogramMainSpec extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with GivenWhenThen{

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Parser actor" should {

    "return AddToResult action with Map[String, Int] of words presented in input block" in {
      Given("Parser actor and input block of lines")
      val parser = system.actorOf(Props[Parser]())
      val inputBlock = Seq("test, test test2", "test3, test test2")

      When("Parser receives Block message with input block of lines")
      parser ! Block(inputBlock)

      Then("""it should return message Map("test" -> 2, "test2" -> 1) """)
      expectMsg(AddToResult(Map("test" -> 3, "test2" -> 2, "test3" -> 1)))
    }

  }

  "Reducer actor" should {

    "return nothing for AddToResult message" in {
      Given("Reducer actor and input map")
      val reducer = system.actorOf(Props[Reducer]())
      val input = Map("test" -> 2, "test2" -> 1)

      When("Reducer receives AddToResult message with input map")
      reducer ! AddToResult(input)

      Then("it should return no message")
      expectNoMessage
    }

    "accumulate some number of maps received in AddToResult messages and return sorted result list of tuples after last map is received" in {
      Given("Reducer actor and two input maps")
      val reducer = system.actorOf(Props[Reducer]())
      val numberOfMaps = 2
      val input = Map("test" -> 2, "test2" -> 1)
      val input2 = Map("test" -> 1, "test3" -> 2)

      When("Reducer receives AddToResult messages with input maps and message GetReduceResult")
      reducer ! NumberOfBlocks(numberOfMaps)
      reducer ! AddToResult(input)
      reducer ! AddToResult(input2)

      Then("""it should return message ReduceResult(List(("test",3), ("test3",2), ("test2",1))) """)
      expectMsg(ReduceResult(List(("test",3), ("test3",2), ("test2",1))))
    }

  }
}
