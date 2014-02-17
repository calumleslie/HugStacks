package com.github.calumleslie.hugs.stacks.lang

import scala.collection.immutable.Seq

sealed trait Particle
case class Quotation(particles: Seq[Particle]) extends Particle
case class Word(name: String) extends Particle
case class Bool(value: Boolean) extends Particle
case class FixNum(value: Long) extends Particle
case class FloatNum(value: Double) extends Particle
case class Str(value: String) extends Particle

object Bool {
  def fromStr(str: String): Bool = str match {
    case "t" => Bool(true)
    case "f" => Bool(false)
    case _ => throw new IllegalArgumentException()
  }
}