package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Particle
import com.github.calumleslie.hugs.stacks.lang.Quotation

class PureDefinition(val definition: Seq[Particle]) extends Definition {
  def apply(state: State) = state.withCallstack(List(definition: _*) :: state.callStack)

  override def toString() = definition.mkString(" ")
}

object PureDefinition {
  def apply(quotation: Quotation): PureDefinition = new PureDefinition(quotation.particles)
  def apply(code: String): PureDefinition = new PureDefinition(Parser.parse(code))
}