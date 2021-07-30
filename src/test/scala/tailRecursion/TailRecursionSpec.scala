package tailRecursion

import org.scalatest.flatspec.AnyFlatSpec

class TailRecursionSpec extends AnyFlatSpec{

  "TailRecursion.recursionIntToString" should "have string 13,9,5,1,-3,1,5,9,13" in {
    assert(TailRecursion.recursionIntToString(13) == "13,9,5,1,-3,1,5,9,13")
  }

  it should "have string -1" in {
    assert(TailRecursion.recursionIntToString(-1) == "-1")
  }

  it should "work correct with n^4 numbers" in {
    assert(TailRecursion.recursionIntToString(16) == "16,12,8,4,0,4,8,12,16")
  }

}
