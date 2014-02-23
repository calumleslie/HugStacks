package com.github.calumleslie.hugs.stacks

import scala.collection.immutable.Map
import com.github.calumleslie.hugs.stacks.lib.Maths
import com.github.calumleslie.hugs.stacks.lib.StackManip
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Word
import com.typesafe.scalalogging.slf4j.Logging
import scala.collection.immutable.Stream
import java.nio.file.Paths
import java.io.FileOutputStream
import java.io.PrintStream

object Horrible extends App with Logging {
  val parser = new Parser
  val program = parser.parse( """
      empty-list "values" let
      5 "count" let
      [ 
        "count" get 1 -
        dup "count" let
        dup "values" append-to-var
        0 != 
      ] loop
      """ )

//  val program = parser.parse( """
//      empty-list "values" let 5 "values" append-to-var
//      """ )
  
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
  
  val htmlOut = Paths.get("/tmp/hello.html")
  
  val out = new PrintStream( new FileOutputStream( htmlOut.toFile() ) )
  
  out.println(initial.makeManualHtml)
  
  out.close()
  
  //  for (i <- 1 to 10) {
  //    state = state.step.get
  //    println(state.stackStr)
  //    println(state.traceStr)
  //  }
}