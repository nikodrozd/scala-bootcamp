package histogramactors

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}

import java.io.PrintWriter
import scala.collection.mutable
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.language.postfixOps
import scala.util._

object HistogramMain extends App {
  val inputFilePath = "src/main/resources/histogram/input.txt"
  val outputFilePath = "src/main/resources/histogram/output.txt"

  val system = ActorSystem("histogram")
  val master = system.actorOf(Props(classOf[Master], inputFilePath, outputFilePath, system), "master")
  master ! DoTheJob

}

class Master(inputFilePath: String, outputFilePath: String, system: ActorSystem) extends Actor {
  private val poolSize = 10
  private var nextParserIndex = 0

  private val reducer: ActorRef = context.actorOf(Props[Reducer], "reducer")
  private val parserPool: IndexedSeq[ActorRef] = (1 to poolSize).map(i => context.actorOf(Props(classOf[Parser]), s"parser$i"))

  override def receive: Receive = {
    case DoTheJob =>
      val dataFromFile: Seq[String] = loadData(inputFilePath) match {
        case Success(value) => value
        case Failure(exception) => throw exception
      }
      val blockSize = (dataFromFile.size / 10) + 1
      val groupedData = dataFromFile.grouped(blockSize).toSeq
      reducer ! NumberOfBlocks(groupedData.size)
      for (block <- groupedData) {
        if (nextParserIndex >= poolSize) nextParserIndex = 0
        parserPool(nextParserIndex) ! Block(block)
        nextParserIndex += 1
      }

    case parseResult @ AddToResult(_) => reducer ! parseResult

    case ReduceResult(result) =>
      saveData(outputFilePath, result) match {
        case Success(_) =>
          system.terminate()
        case Failure(exception) =>
          throw exception
          system.terminate()
      }
  }

  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
      case _ => Restart
    }

  private def loadData(fileName: String): Try[Seq[String]] = {
    Using(Source.fromFile(fileName)) {
      _.getLines().toSeq
    }
  }

  private def saveData(outputFilePath: String, result: Seq[(String, Int)]): Try[Unit] = {
    Using(new PrintWriter(outputFilePath)) {
      writer => {
        result.foreach{case (word, qty) => writer.write(s"$word : $qty\n")}
      }
    }
  }

}

/**
 * Class Parser has if condition for Block case to emulate 20% fail rate
 */
class Parser() extends Actor {

  override def receive: Receive = {
    case Block(block) =>
      if (Random.nextInt(5) != 1) {
        sender() ! AddToResult(block.flatMap(_.split("[^\\d\\w]+")).groupBy(_.toLowerCase).map{case (word, qty) => (word, qty.length)})
      } else throw new RuntimeException("random fail of " + block)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    message match {
      case Some(value) => self.tell(value, context.parent)
      case None =>
    }
  }
}

class Reducer extends Actor {
  private val result = new mutable.TreeMap[String, Int]()
  private var numberOfBlocks = 0

  override def receive: Receive = {
    case AddToResult(map) =>
      map.foreach{case (key, value) => addOrUpdate(key, value)}
      numberOfBlocks -= 1
      if (numberOfBlocks == 0) {
        sender() ! ReduceResult(result.toSeq.sortWith(_._2 > _._2))
        result.empty
      }
    case NumberOfBlocks(num) =>
      numberOfBlocks = num
  }

  private def addOrUpdate(key: String, newValue: Int): Unit = {
    result.get(key) match {
      case Some(value) => result.update(key, value + newValue)
      case None => result.addOne(key, newValue)
    }
  }
}