package com.github.calumleslie.hugs.stacks

import scala.collection.immutable.Map
import com.github.calumleslie.hugs.stacks.lib.Maths
import com.github.calumleslie.hugs.stacks.lib.StackManip
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Word
import com.typesafe.scalalogging.slf4j.Logging

object Horrible extends App with Logging {
  val parser = new Parser
  val program = parser.parse( """
      5 "count" let [ "count" get 1 - dup "count" let 0 != ] loop
      """.trim() )

  val initial = State(program, lib.complete)

  val trace = try {
    initial.trace
  } catch {
  	case e: TraceException => {
      logger.error( "Trace failed!", e )
      e.trace
    }
  }
  
  println(trace.map(_.toShortString()).mkString("\n"))

  //  for (i <- 1 to 10) {
  //    state = state.step.get
  //    println(state.stackStr)
  //    println(state.traceStr)
  //  }
}