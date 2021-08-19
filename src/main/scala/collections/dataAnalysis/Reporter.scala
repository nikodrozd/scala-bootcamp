package collections.dataAnalysis

object Reporter {

  def primaryToOtherTimeCorrelation(people: Stream[Person]): String = {
    val (totalPrimary, totalOther) = people.map(p => (p.timePrimary, p.timeWorking + p.timeOther)).reduce((x, y) => (x._1 + y._1, x._2 + y._2))
    val primPercent = BigDecimal(100.0 * totalPrimary.toDouble/ (totalPrimary + totalOther)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    s"$primPercent% : ${100 - primPercent}%"
  }

  def womenAndMenWorkingTimeComparison(people: Stream[Person]): String = {
    val (female, male) = people.partition(_.gender == Gender.Female)
    val avgFemaleWT = if (female.nonEmpty) female.map(_.timeWorking).sum.toDouble / female.length else 0.0
    val avgMaleWT = if (male.nonEmpty) male.map(_.timeWorking).sum.toDouble / male.length else 0.0
    val avgFemale = BigDecimal(avgFemaleWT).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    val avgMale = BigDecimal(avgMaleWT).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    (avgFemale, avgMale) match {
      case (w, m) if w > m => s"No, in average women spend more time to work ($w) then men ($m)"
      case (w, m) if w < m => s"No, in average men spend more time to work ($m) then women ($w)"
      case (w, m) if w == m => s"Yes, in average women and men spend to work same amount of time - $w"
    }
  }

  def diffAgePeriodPrimaryTimeComparison(people: Stream[Person]): String = {
    val (avgYoungPT, _, avgActivePT, _, avgElderPT, _) = people.foldLeft((0.0, 1.0, 0.0, 1.0, 0.0, 1.0))((acc, person) =>
      person.agePeriod match {
        case AgePeriod.Young => (acc._1 + (person.timePrimary - acc._1) / acc._2, acc._2 + 1, acc._3, acc._4, acc._5, acc._6)
        case AgePeriod.Active => (acc._1, acc._2, acc._3 + (person.timeWorking - acc._3) / acc._4, acc._4 + 1, acc._5, acc._6)
        case AgePeriod.Elder => (acc._1, acc._2, acc._3, acc._4, acc._5 + (person.timeWorking - acc._5) / acc._6, acc._6 + 1)
      }
    )
    val avgYoung = BigDecimal(avgYoungPT).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val avgActive = BigDecimal(avgActivePT).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val avgElder = BigDecimal(avgElderPT).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    (avgYoung, avgActive, avgElder) match {
      case (y, a, e) if y == a & a == e => s"No, average time spent on primary needs are same for all people: $y"
      case (y, a, e) => s"Yes, there is different average time spent on primary needs for diff age periods: Young = $y, Active = $a, Elder = $e"
    }
  }

  def employedToUnemployedOtherTimeCorrelation(people: Stream[Person]): String = {
    val (totalEmployed, _, totalUnemployed, _) = people.foldLeft((0.0, 1.0, 0.0, 1.0))((acc, person) =>
      person.workStatus match {
        case WorkStatus.Working => (acc._1 + (person.timeOther - acc._1) / acc._2, acc._2 + 1, acc._3, acc._4)
        case _ => (acc._1, acc._2, acc._3 + (person.timeOther - acc._3) / acc._4, acc._4 + 1)
      }
    )
    val emplPercent = BigDecimal(100 * totalEmployed/ (totalEmployed + totalUnemployed)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    s"$emplPercent% : ${100 - emplPercent}%"
  }

}
