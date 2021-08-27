package collections.dataAnalysis

import scala.util.{Failure, Success}

object Main {

  val fileName: String = "atussum.csv"
  val numberOfRecords: Int = 10000

  def main(args: Array[String]): Unit = {
    Loader.getPeopleFromFile(fileName, numberOfRecords) match {
      case Failure(ex) => throw ex
      case Success(people) =>
        val start: Long = System.currentTimeMillis()
        println(s"Analysis of file $fileName has been started...")
        println()
        println("1) how much time do we spend on primary needs compared to other activities?\n"
          + Reporter.primaryToOtherTimeCorrelation(people))
        println()
        println("2) do women and men spend the same amount of time in working?")
        println(Reporter.womenAndMenWorkingTimeComparison(people))
        println()
        println("3) does the time spent on primary needs change when people get older?")
        println(Reporter.diffAgePeriodPrimaryTimeComparison(people))
        println()
        println("4) how much time do employed people spend on leisure compared to unemployed people?")
        println(Reporter.employedToUnemployedOtherTimeCorrelation(people))
        println()
        val end: Long = System.currentTimeMillis()
        println(s"Execution time: ${end - start} millis")
    }
  }

}
