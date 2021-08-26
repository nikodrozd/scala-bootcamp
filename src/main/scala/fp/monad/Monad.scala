package fp.monad

trait Monad[M[_]] {
  def unit[A](a: A): M[A]
  def flatMap[A, B](m: M[A])(f: A => M[B]): M[B]
  def map[A, B](m: M[A])(f: A => B): M[B]
}

object Monad {
  def unit[A, M[_] : Monad](a: A): M[A] = implicitly[Monad[M]].unit(a)

  implicit class MonadOps[A, M[_] : Monad](m: M[A]) {
    def flatMap[B](f: A => M[B]): M[B] = implicitly[Monad[M]].flatMap(m)(f)
    def map[B](f: A => B): M[B] = implicitly[Monad[M]].map(m)(f)
  }
}
