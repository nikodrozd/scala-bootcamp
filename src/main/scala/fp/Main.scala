package fp

object Main extends App{

  trait Monoid[T] {
    def empty: T
    def add(x: T, y: T): T
  }

  object Monoid{

    implicit object intMonoid extends Monoid[Int]{
      override def empty: Int = 0

      override def add(x: Int, y: Int): Int = x + y
    }

    implicit object stringMonoid extends Monoid[String]{
      override def empty: String = ""

      override def add(x: String, y: String): String = x + y
    }

  }

  implicit class MonoidOps[T](val x: T) extends AnyVal {
    def reduceCustom[T: Monoid](y: Iterable[T]): T = y.reduce((a, b) => implicitly[Monoid[T]].add(a, b))
  }

  println(Monoid.reduceCustom(Seq(1, 2, 3)))

  println(Monoid.reduceCustom(List("1", "2", "3")))

}

