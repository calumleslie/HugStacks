package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.PureDefinition
import scala.collection.immutable.SortedMap

object Functions {

  lazy val dictionary = SortedMap("apply-to-var" -> applyToVar, "apply" -> apply)

  val apply = Definition("""
      Consumes a quotation andimmediately executes its contents.
      """, { state: State =>
    state.stack match {
      case Quotation(particles) :: rest => state.withStack(rest).pushToCallstack(particles)
      case _ => throw new IllegalArgumentException( //
        "Expected stack to end in quotation but cannot apply to ${state.stackStr}")
    }
  })

  val applyToVar = PureDefinition("""
      Consumes a quotation and a strings, applies the value to the var named by the string, and stores the transformed value into the same var.
      """, """
      [
        [
          "__apply_var" get get
          "__apply_func" get apply
          "__apply_var" get let
        ] swap "__apply_func" with-var
      ] swap "__apply_var" with-var
      """)
}