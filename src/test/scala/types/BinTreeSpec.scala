package types

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BinTreeSpec extends AnyFlatSpec with Matchers with GivenWhenThen {

  "BinTreeNode" should "be able to be created as single node" in {
    Given("key and value parameters for BinTreeNode")
    val key: Int = 1
    val value: String = "test"

    When("new BinTreeNode is created with given parameters")
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

    Then("given value should be available by key using get function")
    binTree.get(key).getOrElse("nothing") should equal(value)
  }

  it should "be immutable, so adding of new element should not change original BinTreeNode" in {
    Given("BinTreeNode with one node")
    val key: Array[Int] = Array(5, 6)
    val value: Array[String] = Array("test5", "test6")
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key(0), value(0))

    When("new node is added with given key and value")
    binTree.add(key(1), value(1))

    Then("original BinTreeNode should not be changed and should return None by given key")
    binTree.get(key(1)) should equal(None)
  }

  "BinTreeNode.add" should "take provided pair of (key, node) and add it to BinTree as new node" in {
    Given("key, value and BinTreeNode with one node")
    val key: Array[Int] = Array(5, 6)
    val value: Array[String] = Array("test5", "test6")
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key(0), value(0))

    When("new node is added to the BinTree with given key and value")
    val newBinTree = binTree.add(key(1), value(1))

    Then("both nodes should be available by given keys")
    newBinTree match {
      case Right(node) =>
        node.get(key(0)).getOrElse("nothing") should equal(value(0))
        node.get(key(1)).getOrElse("nothing2") should equal(value(1))
      case Left(ex) => fail("Test has been failed due to unexpected error: " + ex)
    }
  }

  it should "return IllegalArgumentException exception after trying to add node with duplicate key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val newValue: String = "another test"
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

    When("node with duplicate key is trying to be added to BinTree")
    val newBinTree = binTree.add(key, newValue)

    Then("type of returned Either value should be Left")
    And("type of returned exception should be IllegalArgumentException")
    And("""message of thrown exception should be "Element with key 5 is already exist" """)
    newBinTree.left.map(_.leftSideValue).toString should equal (Left(new IllegalArgumentException(s"Element with key $key is already exist")).toString)
  }

  "BinTreeNode.update" should "take provided pair of (key, value) and update value of node with this key" in {
    Given("key, value and BinTreeNode with two nodes")
    val key: Array[Int] = Array(5, 7)
    val value: Array[String] = Array("test5", "test7")
    val newValue: String = "another test"
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key(0), value(0))

    When("update function is called with given parameters")
    val updatedBinTree: Either[Throwable, BinTreeNode[Int, String]] = for {
      bt <- binTree.add(key(1), value(1))
      bt <- bt.update(key(1), newValue)
    } yield bt

    Then("value of requested node should be updated and another one should remains the same")
    updatedBinTree match {
      case Right(node) =>
        node.get(key(0)).getOrElse("nothing") should equal(value(0))
        node.get(key(1)).getOrElse("nothing2") should equal(newValue)
      case Left(ex) => fail("Test has been failed due to unexpected error: " + ex)
    }
  }

  it should "return NoSuchElementException exception after trying to update value for non-existing key" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val wrongKey: Int = 6
    val newValue: String = "another test"
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

    When("node with non-existing key is trying to be updated in BinTree")
    val newBinTree: Either[Throwable, BinTreeNode[Int, String]] = binTree.update(wrongKey, newValue)

    Then("type of returned Either value should be Left")
    And("type of returned exception should be NoSuchElementException")
    And("""message of thrown exception should be "Element with key 6 is not found" """)
    newBinTree.left.map(_.leftSideValue).toString should equal (Left(new NoSuchElementException(s"Element with key $wrongKey is not found")).toString)
  }

  "BinTreeNode.remove" should "remove node with provided key from BinTree" in {
    Given("key, value and BinTree with one node")
    val key: Array[Int] = Array(5, 7, 6)
    val value: Array[String] = Array("test5", "test7", "test6")
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key(0), value(0))

    When("new non-root node is added to BinTree and remove function is called for this node")
    val newBinTree: Either[Throwable, BinTreeNode[Int, String]] = for {
      bt <- binTree.add(key(1), value(1))
      bt <- bt.add(key(2), value(2))
      bt <- bt.remove(key(2))
    } yield bt

    Then("root node should be still present in BinTree")
    And("removed node should be replaced with None value")
    newBinTree match {
      case Right(node) =>
        node.get(key(0)).getOrElse("nothing") should equal(value(0))
        node.get(key(1)).getOrElse("nothing") should equal(value(1))
        node.get(key(2)) should equal(None)
      case Left(ex) => fail("Test has been failed due to unexpected error: " + ex)
    }
  }

    it should "return NoSuchElementException exception after trying to remove node for non-existing key" in {
      Given("key, value and BinTree with one node")
      val key: Int = 5
      val value: String = "test"
      val wrongKey: Int = 6
      val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

      When("node with non-existing key is trying to be removed from BinTree")
      val newBinTree: Either[Throwable, BinTreeNode[Int, String]] = binTree.remove(wrongKey)

      Then("type of returned Either value should be Left")
      And("type of returned exception should be NoSuchElementException")
      And("""message of thrown exception should be "Element with key 6 is not found" """)
      newBinTree.left.map(_.leftSideValue).toString should equal (Left(new NoSuchElementException(s"Element with key $wrongKey is not found")).toString)
    }

  it should "return IllegalArgumentException exception after trying to remove root node" in {
    Given("key, value and BinTree with one node")
    val key: Int = 5
    val value: String = "test"
    val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

    When("node with non-existing key is trying to be removed from BinTree")
    val newBinTree: Either[Throwable, BinTreeNode[Int, String]] = binTree.remove(key)

    Then("type of returned Either value should be Left")
    And("type of returned exception should be IllegalArgumentException")
    And("""message of thrown exception should be "Impossible to remove root node with key 5" """)
    newBinTree.left.map(_.leftSideValue).toString should equal (Left(new IllegalArgumentException(s"Impossible to remove root node with key $key")).toString)
  }

    "BinTree.getWithDefaultValue" should "return provided default value if nothing was found by provided key" in {
      Given("key, value and BinTree with one node")
      val key: Int = 5
      val value: String = "test"
      val defaultValue = "no elements found"
      val wrongKey: Int = 6
      val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

      When("getWithDefaultValue function is called with default parameter for non-existing key")
      val result = binTree.getWithDefaultValue(wrongKey, defaultValue)

      Then("result of function should contain default value in Option")
      result should equal(defaultValue)
    }

    "BinTree.isExist" should "return true is node with given key exists in BinTree and false if not" in {
      Given("key, value and BinTree with one node")
      val key: Int = 5
      val value: String = "test"
      val wrongKey: Int = 6
      val binTree: BinTreeNode[Int, String] = new BinTreeNode[Int, String](key, value)

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
