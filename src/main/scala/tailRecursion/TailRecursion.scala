package tailRecursion

import java.util.Scanner

object TailRecursion {
  def main(args: Array[String]): Unit = {
    val sc: Scanner = new Scanner(System.in)
    println(recursionString(sc.nextInt()))
  }


  def recursionString(n: Int): String ={
    import scala.annotation.tailrec
    @tailrec // will fail compilation if method can’t be optimized
    def recursionStringInternal (result: String, tempN: Int): String = {
      if (tempN <= 0) {
        s"$result$tempN${reverseString(result)}"
      } else {
        recursionStringInternal(s"$result$tempN,", tempN - 4)
      }
    }
    recursionStringInternal("", n)
  }

  def reverseString (str: String): String ={
    str.split(",").reverse.mkString(",",",","")
  }

}
