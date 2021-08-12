package collections.dataAnalysis

import scala.collection.View
import scala.io.Source
import scala.util.{Try, Using}

case class Loader() {

  def getPeopleFromFile(fileName: String, numberOfRecords: Int): Try[Stream[Person]] = {
    Using(Source.fromResource(fileName)) {
      bufferedSource => {
        val res = bufferedSource.getLines().take(numberOfRecords).toSeq
        val converter: Converter = Converter(res.head.split(","))
        if (converter.isValidIndexes()) {
          res.tail.view.map(line => convertToPerson(converter, line)).toStream
        } else throw new Exception("Header line is not valid")
      }
    }
  }

  def convertToPerson(converter: Converter, rawData: String): Person = {
    val rawParams = rawData.split(",")
    Person(converter.getId(rawParams), converter.getPrimaryTime(rawParams), converter.getWorkingTime(rawParams),
      converter.getOtherTime(rawParams), converter.getWorkStatus(rawParams), converter.getGender(rawParams), converter.getAgePeriod(rawParams))
  }

}
