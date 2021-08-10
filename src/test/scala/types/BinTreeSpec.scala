package types

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BinTreeSpec extends AnyFlatSpec with Matchers with GivenWhenThen{

  "BinTree" should "be able to be created with root node" in {
    Given("Option(Node) to be added to BinTree")
    val key: Int = 1
    val str: String = "test"
    val node: Option[Node[Int, String]] = Some(Node(key, str))

    When("new BinTree is created with given node")
    val binTree: BinTree[Int, String] = new BinTree[Int, String](node)

    Then("value of this node should be available by key using get function")
    binTree.get(key).getOrElse("nothing") should equal(str)
  }

  it should "be immutable, so adding of new element should not change original BinTree" in {
    Given("key, value and empty BinTree")
    val key: Int = 5
    val value: String = "test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]()

    When("new node is added to the BinTree with given key and value")
    binTree.add(key, value)

    Then("original BinTree should not be changed and should return None by given key")
    binTree.get(key) should equal(None)
  }

  "BinTree.add" should "take provided pair of (key, node) and add it to BinTree as new node" in {
    Given("key, value and empty BinTree")
    val key: Int = 5
    val value: String = "test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]()

    When("new node is added to the BinTree with given key and value")
    val newBinTree = binTree.add(key, value)

    Then("node with given value should be available by given key")
    newBinTree.get(key).getOrElse("nothing") should equal(value)
  }

  it should "throw IllegalArgumentException exception after trying to add node with duplicate key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val newValue: String = "another test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("node with duplicate key is trying to be added to BinTree")
    val thrown = the [IllegalArgumentException] thrownBy binTree.add(key, newValue)

    Then("""message of thrown exception should be "Element with key 5 is already exist" """)
    thrown.getMessage should equal(s"Element with key $key is already exist")
  }

  "BinTree.update" should "take provided pair of (key, node) and update value of node with this key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val newValue: String = "another test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("update function is called with given parameters")
    val newBinTree = binTree.update(key, newValue)

    Then("value of requested node should be updated")
    newBinTree.get(key).getOrElse("nothing") should equal(newValue)
  }

  it should "throw NoSuchElementException exception after trying to update value for non-existing key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val wrongKey: Int = 6
    val newValue: String = "another test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("node with non-existing key is trying to be updated in BinTree")
    val thrown = the [NoSuchElementException] thrownBy binTree.update(wrongKey, newValue)

    Then("""message of thrown exception should be "Element with key 6 is not found" """)
    thrown.getMessage should equal(s"Element with key $wrongKey is not found")
  }

  "BinTree.remove" should "remove node with provided key from BinTree" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("remove function is called with given key")
    val newBinTree = binTree.remove(key)

    Then("node with such key should be removed and BinTree.get should return None for this key")
    newBinTree.get(key) should equal(None)
  }

  it should "throw NoSuchElementException exception after trying to remove node for non-existing key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val wrongKey: Int = 6
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("node with non-existing key is trying to be removed from BinTree")
    val thrown = the [NoSuchElementException] thrownBy binTree.remove(wrongKey)

    Then("""message of thrown exception should be "Element with key 6 is not found" """)
    thrown.getMessage should equal(s"Element with key $wrongKey is not found")
  }

  "BinTree.getWithDefaultValue" should "return provided default value if nothing was found by provided key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val defaultValue = "no elements found"
    val wrongKey: Int = 6
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("getWithDefaultValue function is called with default parameter for non-existing key")
    val result = binTree.getWithDefaultValue(wrongKey, defaultValue)

    Then("result of function should contain default value in Option")
    result.getOrElse("nothing") should equal(defaultValue)
  }

  "BinTree.isExist" should "return true is node with given key exists in BinTree and false if not" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val wrongKey: Int = 6
    val binTree: BinTree[Int, String] = new BinTree[Int, String]().add(key, value)

    When("isExist function is called with correct key")
    val result = binTree.isExist(key)
    And("isExist function is called with wrong key")
    val falseResult = binTree.isExist(wrongKey)

    Then("result for correct key should be true")
    result should equal(true)
    And("result for wrong key should be false")
    falseResult should equal(false)
  }

}
