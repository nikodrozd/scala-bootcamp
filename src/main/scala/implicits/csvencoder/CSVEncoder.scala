package implicits.csvencoder
import implicits.csvencoder.CSVEncoderImplicits._


trait CSVEncoder {
  def toCSV[T: Ordering](records: List[T]): String
}

object CSVEncoderImplicits {
  implicit case object BasicCSVEncoder extends CSVEncoder {
    override def toCSV[T: Ordering](records: List[T]): String = {
      records.sorted(Ordering[T].reverse).mkString(",")
    }
  }

  implicit class CSVEncoderOps[T: Ordering](data: List[T]) {
    def toCSV(implicit encoder: CSVEncoder): String = encoder.toCSV(data)
  }
}

case class Person(name: String, age: Int) extends Ordered[Person] {
  override def compare(that: Person): Int = this.age.compare(that.age)

  override def toString: String = s"$name$age"
}

object Main extends App {

  val list: List[Int] = List(1, 5, -3, 3)
  val list2: List[Double] = List(1.2, 2.4, -0.3)
  val list3: List[String] = List("a", "c", "b")
  val list4: List[Person] = List(Person("John", 32), Person("Jack", 20), Person("Tom", 25))

  println(list4.toCSV)

}
