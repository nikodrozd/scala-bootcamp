package tailRecursion

import java.util.Scanner

object TailRecursion {
  def recursionIntToString(n: Int): String ={
    import scala.annotation.tailrec
    @tailrec // will fail compilation if method can’t be optimized
    def recursionIntToStringInternal (result: Array[Int], tempN: Int): String = {
      if (tempN <= 0) {
        resultString(result, tempN)
      } else {
        recursionIntToStringInternal(result :+ tempN, tempN - 4)
      }
    }
    recursionIntToStringInternal(Array[Int](), n)
  }

  def resultString(result: Array[Int], currentTemp: Int): String = {
    ((result :+ currentTemp) ++ result.reverse).mkString(",")
  }

}
