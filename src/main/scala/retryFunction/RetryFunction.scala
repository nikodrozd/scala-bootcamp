package retryFunction

import scala.annotation.tailrec

object RetryFunction {

  @tailrec
  def retry[T](block: () => T,
               accept: T => Boolean,
               retries: List[Long]): T = {
    val res: T = block()
    if (accept(res) || retries.isEmpty) {
      res
    } else {
      Thread.sleep(retries.head)
      println(s"Check failed. Waiting for ${retries.head} millis and retry")
      retry(block: () => T, accept: T => Boolean, retries.tail: List[Long])
    }

  }

}
