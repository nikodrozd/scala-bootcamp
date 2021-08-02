package retryFunction

import org.scalatest.flatspec.AnyFlatSpec

class RetryFunctionSpec extends AnyFlatSpec{

  "RetryFunction.retry" should "return '3' without additional retries" in {
    assert(3 == RetryFunction.retry[Int](
      block = () => 1 + 2,
      accept = res => res > 0,
      retries = List(100, 1000, 2000)
    ))
  }

}
