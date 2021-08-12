package collections.ladder

object Ladder {

  def main(args: Array[String]): Unit = {
    val maxStep: Int = 10
    val listSize: LazyList[Int] = calcWays(maxStep)
    println(s"Number of possible ways: ${listSize.size}")
  }

  def calcWays(n: Int): LazyList[Int] = {
    if (n <= 0) LazyList.empty
    else n #:: calcWays(n - 5) #::: calcWays(n - 3) #::: calcWays(n - 2)
  }


}
