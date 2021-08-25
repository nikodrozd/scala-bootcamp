package collections.dataAnalysis

import scala.collection.parallel.CollectionConverters._

object Reporter {

  sealed trait Monoid[T] {
    def empty: T
    def add(x: T, y: T): T
  }

  implicit object PersonMonoid extends Monoid[Person] {
    override def empty: Person = Person("monoidPerson")
    override def add(x: Person, y: Person): Person = Person("monoidPerson",
      timePrimary = x.timePrimary + y.timePrimary,
      timeWorking = x.timeWorking + y.timeWorking,
      timeOther = x.timeOther + y.timeOther)
  }

  private def avgTimeWorking(st: Stream[Person]): Double = {
    if (st.isEmpty) 0
    else st.par.reduce((x, y) => PersonMonoid.add(x, y)).timeWorking.toDouble / st.length
  }

  private def avgTimeOther(st: Stream[Person]): Double = {
    if (st.isEmpty) 0
    else st.par.reduce((x, y) => PersonMonoid.add(x, y)).timeOther.toDouble / st.length
  }

  def primaryToOtherTimeCorrelation(people: Stream[Person]): String = {
    val totalPerson = (PersonMonoid.empty #:: people).par.reduce((x, y) => PersonMonoid.add(x, y))
    val primPercent = BigDecimal(100.0 * totalPerson.timePrimary.toDouble/ (totalPerson.timePrimary + totalPerson.timeWorking + totalPerson.timeOther)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    s"$primPercent% : ${100 - primPercent}%"
  }

  def womenAndMenWorkingTimeComparison(people: Stream[Person]): String = {
    val (female, male) = people.partition(_.gender == Gender.Female)
    val avgFemale = BigDecimal(avgTimeWorking(female)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    val avgMale = BigDecimal(avgTimeWorking(male)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    (avgFemale, avgMale) match {
      case (w, m) if w > m => s"No, in average women spend more time to work ($w) then men ($m)"
      case (w, m) if w < m => s"No, in average men spend more time to work ($m) then women ($w)"
      case (w, m) if w == m => s"Yes, in average women and men spend to work same amount of time - $w"
    }
  }

  def diffAgePeriodPrimaryTimeComparison(people: Stream[Person]): String = {
    val peopleByAge = people.groupBy(_.agePeriod)
    val avgYoung = BigDecimal(avgTimeWorking(peopleByAge(AgePeriod.Young))).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    val avgActive = BigDecimal(avgTimeWorking(peopleByAge(AgePeriod.Active))).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    val avgElder = BigDecimal(avgTimeWorking(peopleByAge(AgePeriod.Elder))).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    (avgYoung, avgActive, avgElder) match {
      case (y, a, e) if y == a & a == e => s"No, average time spent on primary needs are same for all people: $y"
      case (y, a, e) => s"Yes, there is different average time spent on primary needs for diff age periods: Young = $y, Active = $a, Elder = $e"
    }
  }

  def employedToUnemployedOtherTimeCorrelation(people: Stream[Person]): String = {
    val (employed, unemployed) = people.partition(_.workStatus == WorkStatus.Working)
    val avgEmployedTO = avgTimeOther(employed)
    val avgUnemployedTO = avgTimeOther(unemployed)
    val emplPercent = BigDecimal(100 * avgEmployedTO/ (avgEmployedTO + avgUnemployedTO)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    s"$emplPercent% : ${100 - emplPercent}%"
  }

}
