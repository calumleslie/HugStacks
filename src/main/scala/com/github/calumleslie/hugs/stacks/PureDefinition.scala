package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Particle
import com.github.calumleslie.hugs.stacks.lang.Quotation

class PureDefinition(docs: String, val definition: Seq[Particle]) extends Definition {
  def apply(state: State) = state.withCallstack(List(definition: _*) :: state.callStack)

  def withDocumentation(newDocs: String) = new PureDefinition(newDocs, definition)

  override def documentation = docs
  override def toString() = definition.mkString(" ")
}

object PureDefinition {
  def apply(definition: Seq[Particle]): PureDefinition = new PureDefinition("(no documentation)", definition)
  def apply(quotation: Quotation): PureDefinition = apply(quotation.particles)
  def apply(code: String): PureDefinition = apply(Parser.parse(code))
  def apply(documentation: String, quotation: Quotation): PureDefinition = new PureDefinition(documentation, quotation.particles)
  def apply(documentation: String, code: String): PureDefinition = new PureDefinition(documentation, Parser.parse(code))
}