package histogramactors

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class HistogramMainSpec extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with GivenWhenThen{

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Loader actor" should {

    "return LoadResult for Load message with correct input file path" in {
      Given("Loader actor and path to input file")
      val loader = system.actorOf(Props[Loader]())
      val path = "src/main/resources/histogram/testInput.txt"

      When("Loader receives Load message with input file path")
      loader ! Load(path)

      Then("""it should return message LoadResult(List("123", "test", "test test2")) """)
      expectMsg(LoadResult(List("123", "test", "test test2")))
    }

    "return FailResult for Load message with wrong input file path" in {
      Given("Loader actor and wrong path to input file")
      val loader = system.actorOf(Props[Loader]())
      val wrongPath = "test/test"

      When("Loader receives Load message with input file path")
      loader ! Load(wrongPath)

      Then("it should return FailResult message")
      expectMsgType[FailResult]
    }
  }

  "Parser actor" should {

    "return Map[String, Int] of words presented in input block" in {
      Given("Parser actor and input block of lines")
      val parser = system.actorOf(Props[Parser]())
      val inputBlock = Seq("test, test test2", "test3, test test2")

      When("Parser receives Block message with input block of lines")
      parser ! Block(inputBlock)

      Then("""it should return message Map("test" -> 2, "test2" -> 1) """)
      expectMsg(Map("test" -> 3, "test2" -> 2, "test3" -> 1))
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

    "accumulate all maps received in AddToResult messages and return sorted result list of tuples after GetReduceResult" in {
      Given("Reducer actor and two input maps")
      val reducer = system.actorOf(Props[Reducer]())
      val input = Map("test" -> 2, "test2" -> 1)
      val input2 = Map("test" -> 1, "test3" -> 2)

      When("Reducer receives AddToResult messages with input maps and message GetReduceResult")
      reducer ! AddToResult(input)
      reducer ! AddToResult(input2)
      reducer ! GetReduceResult

      Then("""it should return message ReduceResult(List(("test",3), ("test3",2), ("test2",1))) """)
      expectMsg(ReduceResult(List(("test",3), ("test3",2), ("test2",1))))
    }

  }

  "Mapper actor" should {

    "map received list of lines using ParserPool and Reduced actors, so finally reduced lines should be available by sending GetReduceResult message to Reducer" in {
      Given("Mapper, Reducer, ParserPool actors and list of input lines")
      val poolSize = 10
      val reducer: ActorRef = system.actorOf(Props[Reducer])
      val parserPool: ActorRef = system.actorOf(Props(classOf[ParserPool], poolSize))
      val mapper: ActorRef = system.actorOf(Props(classOf[Mapper], parserPool, reducer))
      val inputLines = Seq("test, test2 test", "test - test3 test3")

      When("Mapper receives DocumentToMap message with input lines")
      mapper ! DocumentToMap(inputLines)
      And("returns MappingFinished message")
      expectMsg(MappingFinished)
      And("Reducer receives GetReduceResult message")
      reducer ! GetReduceResult

      Then("""it should return message ReduceResult(List(("test",3), ("test3",2), ("test2",1))) """)
      expectMsg(ReduceResult(List(("test",3), ("test3",2), ("test2",1))))
    }

  }

  "Saver actor" should {

    "receive Save message with content and output file path and then save received content to provided file and finally return SuccessResult message" in {
      Given("Saver actor, content to save, output file path and loader actor")
      val saver: ActorRef = system.actorOf(Props[Saver]())
      val content: Seq[(String, Int)] = Seq(("test",3), ("test3",2), ("test2",1))
      val path = "src/main/resources/histogram/output.txt"
      val loader = system.actorOf(Props[Loader]())

      When("Saver receives Save message")
      saver ! Save(path, content)

      Then("it should return SuccessResult message")
      expectMsg(SuccessResult)
      And("output file should contain provided content")
      loader ! Load(path)
      expectMsg(LoadResult(List("test : 3", "test3 : 2", "test2 : 1")))
    }

  }
}
