package types

import scala.Ordering.Implicits.infixOrderingOps

class BinTree[K : Ordering, V](root: Option[Node[K, V]] = None) {

  def get(key: K): Option[V] = findNode(key, root).map(_.value)
  def getWithDefaultValue(key: K, value: V): Option[V] = this.get(key).orElse(Some(value))
  def add(key: K, value: V): BinTree[K, V] = new BinTree[K, V](addNode(key, value, root))
  def update(key: K, value: V): BinTree[K, V] = new BinTree[K, V](updateNode(key, value, root))
  def remove(key: K) = new BinTree[K, V](removeNode(key, root))
  def isExist(key: K): Boolean = findNode(key, root).isDefined

  protected def findNode(key: K, rootNode: Option[Node[K, V]]): Option[Node[K, V]] = rootNode match {
    case Some(node) if key == node.key => rootNode
    case Some(node) if key > node.key => findNode(key, node.nodeRight)
    case Some(node) if node.key > key => findNode(key, node.nodeLeft)
    case _ => None
  }

  protected def addNode(key: K, value: V, rootNode: Option[Node[K, V]]): Option[Node[K, V]] = rootNode match {
    case None => Some(Node(key, value))
    case Some(node) if node.key < key => Some(Node(node.key, node.value, node.nodeLeft, addNode(key, value, node.nodeRight)))
    case Some(node) if node.key > key => Some(Node(node.key, node.value, addNode(key, value, node.nodeLeft), node.nodeRight))
    case Some(node) if node.key == key => throw new IllegalArgumentException(s"Element with key $key is already exist")
  }

  protected def updateNode(key: K, value: V, rootNode: Option[Node[K, V]]): Option[Node[K, V]] = rootNode match {
    case Some(node) if node.key == key => Some(Node(key, value, node.nodeLeft, node.nodeRight))
    case Some(node) if node.key < key => Some(Node(node.key, node.value, node.nodeLeft, updateNode(key, value, node.nodeRight)))
    case Some(node) if node.key > key => Some(Node(node.key, node.value, updateNode(key, value, node.nodeLeft), node.nodeRight))
    case None => throw new NoSuchElementException(s"Element with key $key is not found")
  }

  protected def removeNode(key: K, rootNode: Option[Node[K, V]]): Option[Node[K, V]] = rootNode match {
    case Some(node) if node.key == key => None
    case Some(node) if node.key < key => removeNode(key, node.nodeLeft)
    case Some(node) if node.key > key => removeNode(key, node.nodeRight)
    case None => throw new NoSuchElementException(s"Element with key $key is not found")
  }

}

case class Node[K : Ordering, V](key: K, value: V, nodeLeft: Option[Node[K, V]] = None, nodeRight: Option[Node[K, V]] = None) extends BinTree[K, V]


