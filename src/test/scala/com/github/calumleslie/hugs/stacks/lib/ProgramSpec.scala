package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Parser
import com.github.calumleslie.hugs.stacks.State
import com.typesafe.scalalogging.slf4j.Logging

trait ProgramSpec extends Logging {
  def evalOf(code: String) = {
    val program = Parser.parse(code)

    val initial = State(program, complete)

    logger.info(initial.trace.map(_.toShortString()).mkString("\n"))

    initial.eval
  }
}