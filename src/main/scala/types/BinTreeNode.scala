package types

import scala.Ordering.Implicits.infixOrderingOps

case class BinTreeNode[K : Ordering, +V](key: K, value: V, nodeLeft: Option[BinTreeNode[K, V]] = None, nodeRight: Option[BinTreeNode[K, V]] = None) {

  def get(key: K): Option[V] = findNode(key, Some(this)).map(_.value)
  def getWithDefaultValue[A >: V](key: K, value: => A): A = this.get(key).getOrElse(value)
  def add[A >: V](key: K, value: A): Either[Throwable, BinTreeNode[K, A]] = addNode(key, value, rootNode = Some(this))
  def update[A >: V](key: K, value: A): Either[Throwable, BinTreeNode[K, A]] = updateNode(key, value, rootNode = Some(this))
  def remove[A >: V](key: K): Either[Throwable, BinTreeNode[K, A]] = {
    if (key == this.key) Left(new IllegalArgumentException(s"Impossible to remove root node with key $key"))
    else removeNode(key, Some(this))
  }
  def isExist(key: K): Boolean = findNode(key, Some(this)).isDefined

  protected def findNode[A >: V](key: K, rootNode: Option[BinTreeNode[K, A]]): Option[BinTreeNode[K, A]] = rootNode match {
    case Some(node) if key == node.key => rootNode
    case Some(node) if key > node.key => findNode(key, node.nodeRight)
    case Some(node) if node.key > key => findNode(key, node.nodeLeft)
    case _ => None
  }

  protected def addNode[A >: V](key: K, value: A, rootNode: Option[BinTreeNode[K, A]]): Either[Throwable, BinTreeNode[K, A]] = rootNode match {
    case None => Right(BinTreeNode(key, value))
    case Some(node) if node.key < key => newNode(node, addNode(key, value, node.nodeRight), isRightNode = true)
    case Some(node) if node.key > key => newNode(node, addNode(key, value, node.nodeLeft), isRightNode = false)
    case Some(node) if node.key == key => Left(new IllegalArgumentException(s"Element with key $key is already exist"))
  }

  protected def updateNode[A >: V](key: K, value: A, rootNode: Option[BinTreeNode[K, A]]): Either[Throwable, BinTreeNode[K, A]] = rootNode match {
    case Some(node) if node.key == key => Right(BinTreeNode(key, value, node.nodeLeft, node.nodeRight))
    case Some(node) if node.key < key => newNode(node, updateNode(key, value, node.nodeRight), isRightNode = true)
    case Some(node) if node.key > key => newNode(node, updateNode(key, value, node.nodeLeft), isRightNode = false)
    case None => Left(new NoSuchElementException(s"Element with key $key is not found"))
  }

  private def newNode[A >: V](node: BinTreeNode[K, A], newNode: Either[Throwable, BinTreeNode[K, A]], isRightNode: Boolean): Either[Throwable, BinTreeNode[K, A]] = {
    newNode match {
      case Right(newNode) =>
        if (isRightNode) Right(BinTreeNode(node.key, node.value, node.nodeLeft, Some(newNode)))
        else Right(BinTreeNode(node.key, node.value, Some(newNode), node.nodeRight))
      case Left(ex) => Left(ex)
    }
  }

  protected def removeNode[A >: V](key: K, rootNode: Option[BinTreeNode[K, A]]): Either[Throwable, BinTreeNode[K, A]] = rootNode match {
    case Some(node) if node.nodeRight.exists(_.key == key) => Right(BinTreeNode(node.key, node.value, node.nodeLeft, None))
    case Some(node) if node.nodeLeft.exists(_.key == key) => Right(BinTreeNode(node.key, node.value, None, node.nodeRight))
    case Some(node) if node.nodeRight.isDefined & node.key < key => newNode(node, removeNode(key, node.nodeRight), isRightNode = true)
    case Some(node) if node.nodeLeft.isDefined & node.key > key => newNode(node, removeNode(key, node.nodeLeft), isRightNode = false)
    case _ => Left(new NoSuchElementException(s"Element with key $key is not found"))
  }

}