package collections.dataAnalysis

trait Monoid[T] {
  def empty: T
  def add(x: T, y: T): T
}
trait MonoidOps[T] {
  def value: T
  def m: Monoid[T]
  def +(other: T): T = m.add(value, other)
}
object Monoid {
  implicit def toMonoidOps[T](t:T)(implicit monoid: Monoid[T]): MonoidOps[T] = new MonoidOps[T] {
    override def value: T = t
    override def m: Monoid[T] = monoid
  }
}
