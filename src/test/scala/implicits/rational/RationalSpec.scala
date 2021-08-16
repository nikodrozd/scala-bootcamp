package implicits.rational

import implicits.rational.RationalConverter._
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RationalSpec extends AnyFlatSpec with Matchers with GivenWhenThen{

  "Rational.+ function" should "return sum of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation + is applied to these objects")
    val ratRes = val1 + val2

    Then("nominator of result Rational should be 17")
    ratRes.nominator should equal(17)
    And("denominator of result Rational should be 12")
    ratRes.denominator should equal(12)
  }

  it should "return sum of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation + is applied to these objects")
    val ratRes: Rational = val1 + val2

    Then("nominator of result Rational should be 14")
    ratRes.nominator should equal(14)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  it should "return sum of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation + is applied to these objects")
    val ratRes: Rational = val1 + val2

    Then("nominator of result Rational should be 14")
    ratRes.nominator should equal(14)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  "Rational.- function" should "return difference of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation - is applied to these objects")
    val ratRes = val1 - val2

    Then("nominator of result Rational should be -1")
    ratRes.nominator should equal(-1)
    And("denominator of result Rational should be 12")
    ratRes.denominator should equal(12)
  }

  it should "return difference of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation - is applied to these objects")
    val ratRes: Rational = val2 - val1

    Then("nominator of result Rational should be 10")
    ratRes.nominator should equal(10)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  it should "return difference of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation - is applied to these objects")
    val ratRes: Rational = val2 - val1

    Then("nominator of result Rational should be 10")
    ratRes.nominator should equal(10)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  "Rational.* function" should "return multiplication of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation * is applied to these objects")
    val ratRes = val1 * val2

    Then("nominator of result Rational should be 6")
    ratRes.nominator should equal(6)
    And("denominator of result Rational should be 12")
    ratRes.denominator should equal(12)
  }

  it should "return multiplication of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation * is applied to these objects")
    val ratRes: Rational = val1 * val2

    Then("nominator of result Rational should be 8")
    ratRes.nominator should equal(8)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  it should "return multiplication of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation + is applied to these objects")
    val ratRes: Rational = val1 * val2

    Then("nominator of result Rational should be 8")
    ratRes.nominator should equal(8)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  "Rational./ function" should "return div of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation / is applied to these objects")
    val ratRes = val1 / val2

    Then("nominator of result Rational should be 8")
    ratRes.nominator should equal(8)
    And("denominator of result Rational should be 9")
    ratRes.denominator should equal(9)
  }

  it should "return div of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation / is applied to these objects")
    val ratRes: Rational = val2 / val1

    Then("nominator of result Rational should be 12")
    ratRes.nominator should equal(12)
    And("denominator of result Rational should be 2")
    ratRes.denominator should equal(2)
  }

  it should "return div of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation / is applied to these objects")
    val ratRes: Rational = val2 / val1

    Then("nominator of result Rational should be 12")
    ratRes.nominator should equal(12)
    And("denominator of result Rational should be 2")
    ratRes.denominator should equal(2)
  }

  "Rational.max function" should "return maximum value of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation max is applied to these objects")
    val ratRes = val1 max val2

    Then("nominator of result Rational should be 3")
    ratRes.nominator should equal(3)
    And("denominator of result Rational should be 4")
    ratRes.denominator should equal(4)
  }

  it should "return maximum value of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation max is applied to these objects")
    val ratRes: Rational = val1 max val2

    Then("nominator of result Rational should be 4")
    ratRes.nominator should equal(4)
    And("denominator of result Rational should be 1")
    ratRes.denominator should equal(1)
  }

  it should "return maximum of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation max is applied to these objects")
    val ratRes: Rational = val1 max val2

    Then("nominator of result Rational should be 4")
    ratRes.nominator should equal(4)
    And("denominator of result Rational should be 1")
    ratRes.denominator should equal(1)
  }

  "Rational.min function" should "return minimum value of two Rational objects as another Rational object" in {
    Given("two Rational objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Rational = new Rational(3, 4)

    When("operation min is applied to these objects")
    val ratRes = val1 min val2

    Then("nominator of result Rational should be 2")
    ratRes.nominator should equal(2)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  it should "return minimum value of Rational and Int objects as another Rational object" in {
    Given("Rational and Int objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Int = 4

    When("operation min is applied to these objects")
    val ratRes: Rational = val1 min val2

    Then("nominator of result Rational should be 2")
    ratRes.nominator should equal(2)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

  it should "return minimum of Rational and Long objects as another Rational object" in {
    Given("Rational and Long objects")
    val val1: Rational = new Rational(2, 3)
    val val2: Long = 4L

    When("operation min is applied to these objects")
    val ratRes: Rational = val1 min val2

    Then("nominator of result Rational should be 2")
    ratRes.nominator should equal(2)
    And("denominator of result Rational should be 3")
    ratRes.denominator should equal(3)
  }

}
