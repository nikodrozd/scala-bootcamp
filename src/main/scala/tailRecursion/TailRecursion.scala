package tailRecursion

import java.util.Scanner

object TailRecursion {
  def main(args: Array[String]): Unit = {
    val sc: Scanner = new Scanner(System.in)
    println(recursionIntToString(sc.nextInt()))
  }


  def recursionIntToString(n: Int): String ={
    import scala.annotation.tailrec
    @tailrec // will fail compilation if method can’t be optimized
    def recursionIntToStringInternal (result: String, tempN: Int): String = {
      if (tempN <= 0) {
        s"$result${reverseString(s"$result$tempN,")}"
      } else {
        recursionIntToStringInternal(s"$result$tempN,", tempN - 4)
      }
    }
    recursionIntToStringInternal("", n)
  }

  def reverseString (str: String): String ={
    str.split(",").reverse.mkString(",")
  }

}
