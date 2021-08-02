package retryFunction

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RetryFunctionSpec extends AnyFlatSpec with Matchers {

  "RetryFunction.retry" should "return '3' without additional retries" in {
    RetryFunction.retry[Int](
      block = () => 1 + 2,
      accept = res => res > 0,
      retries = List(100, 1000, 2000)
    ) should equal(3)
  }

  it should "do only 1 retry" in {
    val util = Utility(-1)
    RetryFunction.retry[Int](
      block = () => util.tempIncrement(),
      accept = res => res == 1,
      retries = List(100, 100, 100)
    )

    //checking that temp variable was increased only 1 time
    util.temp should equal(1)
  }

  it should "do all retries and return -1" in {
    val util = Utility(-1)

    RetryFunction.retry[Int](
      block = () => {
        util.tempIncrement()
        -1
      },
      accept = res => res > 0,
      retries = List(100, 200, 300)
    ) should equal(-1)

    //checking that temp variable was increased 3 times
    util.temp should equal(3)
  }

}

case class Utility(var temp: Int) {
  /*
    Function to count number of retries
     */
  def tempIncrement(): Int = {
    temp += 1
    temp
  }
}
