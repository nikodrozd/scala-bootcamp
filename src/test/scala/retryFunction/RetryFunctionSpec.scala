package retryFunction

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RetryFunctionSpec extends AnyFlatSpec with Matchers with GivenWhenThen{

  "RetryFunction.retry" should "return '3' without additional retries" in {
    Given("input block function '() => 1 + 2' and accept function 'res => res > 0'. Number of potential retries: 3 (100, 1000, 2000)")
    When("retry function is executed")
    Then("result of execution should be 3")
    RetryFunction.retry[Int](
      block = () => 1 + 2,
      accept = res => res > 0,
      retries = List(100, 1000, 2000)
    ) should equal(3)
  }

  it should "do only 1 retry if accept function return true after it" in {
    Given("util variable of Utility type. Only one method tempIncrement is used in this test - for incrementation of initial temp value")
    Given("input block function '() => util.tempIncrement()' and accept function 'res => res == 1'. Number of potential retries: 3 (100, 100, 100)")

    val util = Utility(-1)

    When("retry function is executed")

    RetryFunction.retry[Int](
      block = () => util.tempIncrement(),
      accept = res => res == 1,
      retries = List(100, 100, 100)
    )

    Then("util.temp value should be equal 1 (increase only 1 time)")

    util.temp should equal(1)
  }

  it should "return result of block function if it's not match accept function after all retries" in {
    Given("util variable of Utility type. Only one method tempIncrement is used in this test - for incrementation of initial temp value")
    Given("input block function '() => {\n        util.tempIncrement()\n        -1\n      }' and accept function 'res => res > 0'. Number of potential retries: 3 (100, 200, 300)")

    val util = Utility(-1)

    When("retry function is executed")
    Then("function result value should be equal -1")

    RetryFunction.retry[Int](
      block = () => {
        util.tempIncrement()
        -1
      },
      accept = res => res > 0,
      retries = List(100, 200, 300)
    ) should equal(-1)

    Then("util.temp value should be equal 3 (increase all 3 times)")

    util.temp should equal(3)
  }

  it should "wait specified time before start of next function retry" in {
    Given("util variable of Utility type. Two methods of this type are used in test: tempIncrement, timeExecution. See methods docs for details")
    Given("input block function 'util.tempIncrement()' and accept function 'res => res > 0'. Number of potential retries: 2 (2000, 1000)")
    val util = Utility(-1)

    When("retry function is executed, it's execution time is calculated and stored to time variable")

    val time: Long = util.timeExecution[Int](
    RetryFunction.retry[Int](
      block = () => util.tempIncrement(),
      accept = res => res > 0,
      retries = List(2000, 1000))
    )

    Then("time value should be greater then first retry timeout (2000) and less then sum of all retry timeouts (2000+1000=3000)")

    time should be < 3000L
    time should be > 2000L
  }

}

case class Utility(var temp: Int = 0) {
  /**
   * Function to count number of retries
   * @return incremented temp value
   */
  def tempIncrement(): Int = {
    temp += 1
    temp
  }

  /**
   * Function to calculate input function execution time
   * @param block is input function
   * @tparam R is return type of input function
   * @return input function execution time in millis
   */
  def timeExecution[R](block: => R): Long = {
    val t0 = System.currentTimeMillis()
    block
    val t1 = System.currentTimeMillis()
    t1 - t0
  }
}
