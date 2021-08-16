package implicits.csvencoder

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import implicits.csvencoder.CSVEncoderImplicits._

class CSVEncoderSpec extends AnyFlatSpec with Matchers with GivenWhenThen {

  "CSVEncoder.toCSV function" should "be called for List[Int] and return String of list elements sorted from larger to smaller in CSV format" in {
    Given("list of Int values")
    val values: List[Int] = List(1, 5, -3, 3)

    When("toCSV function is applied to this list")
    val result: String = values.toCSV

    Then("values in list should be sorted from larger to smaller and returned as string in CSV format")
    result should equal("5,3,1,-3")
  }

  it should "be called for List[Double] and return String of list elements sorted from larger to smaller in CSV format" in {
    Given("list of Double values")
    val values: List[Double] = List(1.2, 2.4, -0.3)

    When("toCSV function is applied to this list")
    val result: String = values.toCSV

    Then("values in list should be sorted from larger to smaller and returned as string in CSV format")
    result should equal("2.4,1.2,-0.3")
  }

  it should "be called for List[String] and return String of list elements sorted from larger to smaller in CSV format" in {
    Given("list of String values")
    val values: List[String] = List("a", "c", "b")

    When("toCSV function is applied to this list")
    val result: String = values.toCSV

    Then("values in list should be sorted from larger to smaller and returned as string in CSV format")
    result should equal("c,b,a")
  }

  it should "be called for List[Person] and return String of list elements sorted from larger to smaller in CSV format" in {
    Given("list of Person objects")
    val values: List[Person] = List(Person("John", 32), Person("Jack", 20), Person("Tom", 25))

    When("toCSV function is applied to this list")
    val result: String = values.toCSV

    Then("values in list should be sorted from larger to smaller and returned as string in CSV format")
    result should equal("John32,Tom25,Jack20")
  }

}
