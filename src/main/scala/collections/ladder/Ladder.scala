package collections.ladder

object Ladder {

  def main(args: Array[String]): Unit = {
    val result: LazyList[Int] = calcWays()
    result(15)
    println(result)
  }

  def calcWays(): LazyList[Int] = {
    def calcWaysInternal(n: Int, prev: LazyList[Int]): LazyList[Int] = {
      val current = n match {
        case x if x <= 1 => 0
        case 2 | 3 | 4 => 1
        case 5 => 3
        case _ => prev(n-2) + prev(n-3) + prev(n-5)
      }
      current #:: calcWaysInternal(n + 1, prev #::: current #:: LazyList.empty)
    }
    calcWaysInternal(0, LazyList.empty)
  }
}
