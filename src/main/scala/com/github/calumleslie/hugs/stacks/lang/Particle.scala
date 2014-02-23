package com.github.calumleslie.hugs.stacks.lang

import scala.collection.immutable.Seq

sealed trait Particle {
  /**
   * The form in which this primitive is pushed onto the main stack.
   */
  def toPrimitive: Any
}
case class Quotation(particles: List[Particle]) extends Particle {
  override def toPrimitive = this
  override def toString() = s"[ ${particles.mkString(" ")} ]"
}
case class Word(name: String) extends Particle {
  override def toPrimitive = throw new IllegalArgumentException("Words cannot be converted into primitive values")
  override def toString() = name
}
case class Bool(value: Boolean) extends Particle {
  override def toPrimitive = value
  override def toString() = if (value) "t" else "f"
}
case class FixNum(value: Long) extends Particle {
  override def toPrimitive = value
  override def toString() = value.toString()
}
case class FloatNum(value: Double) extends Particle {
  override def toPrimitive = value
  override def toString() = value.toString
}
case class Str(value: String) extends Particle {
  override def toPrimitive = value
  override def toString() = s""""${value}""""
}

object Quotation {
  def apply(particles: Particle*): Quotation = Quotation(List(particles: _*))
}

object Bool {
  def fromStr(str: String): Bool = str match {
    case "t" => Bool(true)
    case "f" => Bool(false)
    case _ => throw new IllegalArgumentException()
  }
}