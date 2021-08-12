package collections.dataAnalysis

import collections.dataAnalysis.AgePeriod.AgePeriod
import collections.dataAnalysis.Gender.Gender
import collections.dataAnalysis.WorkStatus.WorkStatus

import scala.collection.View

case class Converter(rawHeader: Array[String]) {

  private val timePrimaryRegex = "^(t01|t03|t11|t1801|t1803).*"
  private val timeWorkingRegex = "^(t05|t1805).*"
  private val timeOtherRegex = "^(t02|t04|t06|t07|t08|t09|t10|t12|t13|t14|t15|t16|t18).*"
  private val workStatusRegex = "^telfs"
  private val genderRegex = "^tesex"
  private val agePeriodRegex = "^teage"
  private val idRegex = "^tucaseid"

  private val primaryTimeIndexes: View[Int] = getIndexSeq(timePrimaryRegex)
  private val workingTimeIndexes: View[Int] = getIndexSeq(timeWorkingRegex)
  private val otherTimeIndexes: View[Int] = {
    rawHeader.view.zipWithIndex.filter(_._1.matches(timeOtherRegex))
      .filterNot(_._1.matches(timePrimaryRegex))
      .filterNot(_._1.matches(timePrimaryRegex))
      .map(_._2)
  }
  private val workStatusIndex: Int = getIndex(workStatusRegex)
  private val genderIndex: Int = getIndex(genderRegex)
  private val agePeriodIndex: Int = getIndex(agePeriodRegex)
  private val idIndex: Int = getIndex(idRegex)

  private def getIndexSeq(regex: String): View[Int] = {
    rawHeader.view.zipWithIndex.filter(_._1.matches(regex)).map(_._2)
  }
  private def getIndex(regex: String): Int = {
    rawHeader.view.zipWithIndex.find(_._1.matches(regex)).map(_._2).getOrElse(-1)
  }

  def getPrimaryTime(rawParams: Array[String]): Int = {
    getTime(primaryTimeIndexes, rawParams)
  }

  def getWorkingTime(rawParams: Array[String]): Int = {
    getTime(workingTimeIndexes, rawParams)
  }

  def getOtherTime(rawParams: Array[String]): Int = {
    getTime(otherTimeIndexes, rawParams)
  }

  private def getTime(indexes: View[Int], rawParams: Array[String]) = indexes.map(rawParams).map(_.toInt).sum

  def getWorkStatus(rawParams: Array[String]): WorkStatus = rawParams(workStatusIndex).toInt match {
    case 5 => WorkStatus.NotEmployable
    case ws if ws >= 1 & ws < 3 => WorkStatus.Working
    case _ => WorkStatus.NotWorking
  }

  def getGender(rawParams: Array[String]): Gender = rawParams(genderIndex).toInt match {
    case 1 => Gender.Male
    case _ => Gender.Female
  }

  def getAgePeriod(rawParams: Array[String]): AgePeriod = rawParams(agePeriodIndex).toInt match {
    case ap if ap >= 15 & ap <= 22 => AgePeriod.Young
    case ap if ap >= 23 & ap <= 55 => AgePeriod.Active
    case _ => AgePeriod.Elder
  }

  def getId(rawParams: Array[String]): String = rawParams(idIndex)

  def isValidIndexes(): Boolean = {
    if (primaryTimeIndexes.isEmpty | workingTimeIndexes.isEmpty | otherTimeIndexes.isEmpty
      | workStatusIndex == -1 | genderIndex == -1 | agePeriodIndex == -1 | idIndex == -1) false else true
  }
}
