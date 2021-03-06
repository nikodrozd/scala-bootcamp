package extractors

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.ByteArrayOutputStream

class ExtractorSpec extends AnyFlatSpec with Matchers with GivenWhenThen {

  "Email.apply function" should """take local part "test" and domain part "intellias.com" string as arguments and return email string "test@intellias.com" """ in {
    Given("""local part string "test" and domain part string "intellias.com" """)
    val localPart: String = "test"
    val domain: String = "intellias.com"

    When("Email object is created with given parameters (tested function is called automatically)")
    val result = Email(localPart, domain)

    Then("""result value should equal "test@intellias.com" """)
    result should equal("test@intellias.com")
  }

  "Email.unapply function" should """take email string "test@intellias.com" and return value Some(("test", "intellias.com"))""" in {
    Given("email string 'test@intellias.com'")
    val emailString: String = "test@intellias.com"

    When("tested function is called for Email object and given parameter")
    val resultOpt: Option[(String, String)] = Email.unapply(emailString)

    Then("""result object should equal Some(("test", "intellias.com"))""")
    resultOpt should equal(Some(("test", "intellias.com")))
  }

  it should """take incorrect email string "test@intellias@com" and return value None""" in {
    Given("""incorrect email string "test@intellias.com" """)
    val emailString: String = "test@intellias@com"

    When("tested function is called for Email object and given parameter")
    val resultOpt: Option[(String, String)] = Email.unapply(emailString)

    Then("result object should equal None")
    resultOpt should equal(None)
  }

  "Domain.unapplySeq function" should """take domain string "test.com.ua" and return value of Option[Array[String]] type with "ua" in first element""" in {
    Given("""domain string "test.com.ua" """)
    val domain: String = "test.com.ua"

    When("given string is unapplied using match (unapplySeq) and first element is taken as result")
    val reversedHeadDomain: String = domain match {
      case Domain(head, _*) => head
    }

    Then("""result should be value of top-level domain "ua" """)
    reversedHeadDomain should equal("ua")
  }

  it should "return an empty Array for empty input string" in {
    Given("empty domain string")
    val domain: String = ""

    When("given string is unapplied using match (unapplySeq) and first element is taken as result")
    val reversedHeadDomain: String = domain match {
      case Domain(head, _*) => head
    }

    Then("result should be equal to empty string")
    reversedHeadDomain should equal("")
  }

  "Extractor.filterNotDomainsWithParticularHead function" should """take list of domains to input and return filtered list without domains with "in.ua" part""" in {
    Given("""list of domains "test.in.ua", "test.com", "test.failed.in.ua", "test.passed.com", "in.ua" """)
    val domainList: List[String] = List("test.in.ua", "test.com", "test.failed.in.ua", "test.passed.com", "in.ua")

    When("this list is filtered using tested function")
    val filteredList = Extractor.filterNotDomainsWithParticularHead(domainList)

    Then("size of filtered list should be 2")
    filteredList.size should equal(2)
    And("""head element of filtered list should be "test.com" """)
    filteredList.head should equal("test.com")
    And(""" first element of filtered list should be "test.passed.com" """)
    filteredList(1) should equal("test.passed.com")
  }

  it should """return empty list if there are no other domains except "in.ua" in input list""" in {
    Given("""list of domains "test.in.ua", "test.failed.in.ua", "in.ua" """)
    val domainList: List[String] = List("test.in.ua", "test.failed.in.ua", "in.ua")

    When("this list is filtered using tested function")
    val filteredList = Extractor.filterNotDomainsWithParticularHead(domainList)

    Then("size of filtered list should be 0")
    filteredList.size should equal(0)
  }

  "Extractor.printEmailsFilteredByDomain function" should """take array of emails to input and print only emails with "gmail.com" domain (by default)""" in {
    Given("""array of emails "test@gmail.com", "test@in.ua", "holy-scala@gmail.com", "scala@metagmail.com" """)
    val emailArray: Array[String] = Array("test@gmail.com", "test@in.ua", "holy-scala@gmail.com", "scala@metagmail.com")
    val printCheck = new ByteArrayOutputStream()

    When("tested function is called for given array and console output is saved as array of strings")
    Console.withOut(printCheck) {
      Extractor.printEmailsFilteredByDomain(emailArray)
    }
    val consoleResult: Array[String] = printCheck.toString.split("\\r\\n")

    Then("""it should print emails with "gmail.com" domain""")
    consoleResult should contain("test@gmail.com")
    consoleResult should contain("holy-scala@gmail.com")
    And("""should not print emails without "gmail.com" domain""")
    consoleResult shouldNot contain("test@in.ua")
    consoleResult shouldNot contain("scala@metagmail.com")
  }

}
