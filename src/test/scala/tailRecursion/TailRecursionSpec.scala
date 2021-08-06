package tailRecursion

import org.scalatest.flatspec.AnyFlatSpec

class TailRecursionSpec extends AnyFlatSpec{

  "TailRecursion.recursionIntToString" should "return string 13,9,5,1,-3,1,5,9,13 for input value 13" in {
    assert(TailRecursion.recursionIntToString(13) == "13,9,5,1,-3,1,5,9,13")
  }

  it should "return string with one element -1 for negative input value -1" in {
    assert(TailRecursion.recursionIntToString(-1) == "-1")
  }

  it should "return string with 0 as middle element (16,12,8,4,0,4,8,12,16) for input values which are the degree of 4" in {
    assert(TailRecursion.recursionIntToString(16) == "16,12,8,4,0,4,8,12,16")
  }

}
