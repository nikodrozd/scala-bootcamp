package implicits.rational

import scala.language.implicitConversions

class Rational(val nominator: Int, val denominator: Int) {
  override def toString = s"$nominator / $denominator"

  def +(that: Rational): Rational = new Rational(
    nominator * that.denominator + that.nominator * denominator,
    denominator * that.denominator
  )

  def -(that: Rational): Rational = new Rational(
    nominator * that.denominator - that.nominator * denominator,
    denominator * that.denominator
  )

  def *(that: Rational): Rational = new Rational(
    nominator * that.nominator,
    denominator * that.denominator
  )

  def /(that: Rational): Rational = *(new Rational(that.denominator, that.nominator))

  def max(that: Rational): Rational = {
    if (nominator * that.denominator < that.nominator * denominator) that
    else this
  }

  def min(that: Rational): Rational = {
    if (nominator * that.denominator > that.nominator * denominator) that
    else this
  }

}

object RationalConverter {
  implicit def intToRational(x: Int): Rational = new Rational(x, 1)

  implicit def longToRational(x: Long): Rational = new Rational(x.toInt, 1)
}