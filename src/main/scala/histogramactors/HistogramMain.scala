package histogramactors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import java.io.PrintWriter
import scala.collection.mutable
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

object HistogramMain extends App {
  val inputFilePath = "src/main/resources/histogram/input.txt"
  val outputFilePath = "src/main/resources/histogram/output.txt"

  val system = ActorSystem("histogram")
  val master = system.actorOf(Props(classOf[Master], inputFilePath, outputFilePath, system), "master")
  master ! Start

}

class Master(input: String, output: String, system: ActorSystem) extends Actor {
  private val poolSize = 10

  val loader: ActorRef = context.actorOf(Props[Loader](), "loader")
  val reducer: ActorRef = context.actorOf(Props[Reducer], "reducer")
  val parserPool: ActorRef = context.actorOf(Props(classOf[ParserPool], poolSize), "parserPool")
  val mapper: ActorRef = context.actorOf(Props(classOf[Mapper], parserPool, reducer), "mapper")
  val saver: ActorRef = context.actorOf(Props[Saver](), "saver")
  override def receive: Receive = {
    case Start =>
      loader ! Load(input)
    case LoadResult(dataFromFile) =>
      mapper ! DocumentToMap(dataFromFile)
    case MappingFinished =>
      reducer ! GetReduceResult
    case ReduceResult(result) =>
      saver ! Save(output, result)
    case FailResult(ex) =>
      throw ex
      system.terminate()
    case SuccessResult =>
      system.terminate()
  }
}

class Loader extends Actor {
  override def receive: Receive = {
    case Load(filePath) =>
      import scala.concurrent.ExecutionContext.Implicits.global

      val master = sender()
      Future{
        loadData(filePath) match {
          case Success(dataFromFile) => LoadResult(dataFromFile)
          case Failure(exception) => FailResult(exception)
        }
      } onComplete {
        case Success(value) => master ! value
        case Failure(exception) => master ! FailResult(exception)
      }
  }

  private def loadData(fileName: String): Try[Seq[String]] = {
    Using(Source.fromFile(fileName)) {
      _.getLines().toSeq
    }
  }
}

class Parser extends Actor {
  override def receive: Receive = {
    case Block(block) =>
      sender() ! block.flatMap(_.split("[^\\d\\w]+")).groupBy(_.toLowerCase).map{case (word, qty) => (word, qty.length)}
  }
}

class ParserPool(size: Int) extends Actor {
  (1 to size).map(i => context.actorOf(Props(classOf[Parser]), s"parser$i"))
  private implicit val timeout: Timeout = new Timeout(Duration(1000, MILLISECONDS))
  private implicit val executionContext: ExecutionContext = context.dispatcher

  private var nextParserIndex = 0

  override def receive: Receive = {
    case Block(block) =>
      if (nextParserIndex < size) {
        val s = sender()
        (context.children.toIndexedSeq(nextParserIndex) ? Block(block)).pipeTo(s)
        nextParserIndex += 1
      } else nextParserIndex = 0
  }
}

class Mapper(parserPool: ActorRef, reducer: ActorRef) extends Actor {
  private implicit val timeout: Timeout = new Timeout(Duration(1000, MILLISECONDS))
  private implicit val executionContext: ExecutionContext = context.dispatcher

  override def receive: Receive = {
    case DocumentToMap(lines) =>
      val s = sender()
      val blockSize = (lines.size / 10) + 1
      val res: Iterator[Future[Map[String, Int]]] = lines.grouped(blockSize).map(block => parserPool.ask(Block(block)).asInstanceOf[Future[Map[String, Int]]])
      res.foreach(fut => Await.ready(fut, Duration(1100, MILLISECONDS)).map(resMap => reducer ! AddToResult(resMap)))
      s ! MappingFinished
  }
}

class Reducer extends Actor {
  private val result = new mutable.TreeMap[String, Int]()

  override def receive: Receive = {
    case AddToResult(map) => map.foreach{case (key, value) => addOrUpdate(key, value)}
    case GetReduceResult =>
      val resultToSend: Seq[(String, Int)] = result.toSeq.sortWith(_._2 > _._2)
      sender() ! ReduceResult(resultToSend)
  }

  private def addOrUpdate(key: String, newValue: Int): Unit = {
    result.get(key) match {
      case Some(value) => result.update(key, value + newValue)
      case None => result.addOne(key, newValue)
    }
  }
}

class Saver extends Actor {
  override def receive: Receive = {
    case Save(outputFilePath, result) =>
      saveData(outputFilePath, result) match {
        case Success(_) => sender() ! SuccessResult
        case Failure(exception) => sender() ! FailResult(exception)
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