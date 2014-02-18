package com.github.calumleslie.hugs.stacks

import scala.collection.immutable.Map

object Horrible extends App {
  val parser = new Parser
  val program = parser.parse("4 dup add")

  println(program)

  var state = State(program, Map("nop" -> DefaultDefinitions.noop, "dup" -> DefaultDefinitions.dup, "add" -> DefaultDefinitions.add))

  for (i <- 1 to 10) {
    state = state.step.get
    println(state)
  }
}