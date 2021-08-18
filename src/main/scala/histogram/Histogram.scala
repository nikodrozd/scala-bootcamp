package histogram

import java.io.PrintWriter
import scala.collection.parallel.CollectionConverters.ImmutableSeqIsParallelizable
import scala.collection.parallel.ParSeq
import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

object Histogram {

  def loadData(fileName: String): Try[ParSeq[String]] = {
    Using(Source.fromFile(fileName)) {
      bufferedSource => {
        bufferedSource.getLines().toSeq.par.flatMap(_.split("[^\\d\\w]+"))
      }
    }
  }

  def saveData(fileName: String, counted: Seq[(String, Int)]): Unit = {
    Using(new PrintWriter(fileName)) {
      writer => {
        counted.foreach(word => writer.write(s"${word._1} : ${word._2}\n"))
      }
    }
  }

  def countWords(words: ParSeq[String]): Seq[(String, Int)] = {
    words.groupBy(_.toLowerCase).map(x => (x._1, x._2.length)).toSeq.seq.sortWith(_._2 > _._2)
  }

  def printResult(counted: Seq[(String, Int)]): Unit = {
    counted.foreach(word => println(s"${word._1} : ${word._2}"))
  }

}

object Main {

  val inputFileName: String = "src/main/resources/histogram/input.txt"
  val outputFileName: String = "src/main/resources/histogram/output.txt"

  def main(args: Array[String]): Unit = {
    Histogram.loadData(inputFileName) match {
      case Failure(exception) => throw exception
      case Success(value) => Histogram.saveData(outputFileName, Histogram.countWords(value))
    }
  }
}