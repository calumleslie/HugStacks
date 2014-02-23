package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.PureDefinition

object Functions {

  lazy val dictionary = Map("deflocal" -> deflocal, "apply-to-var" -> applyToVar, "apply" -> apply)

  val deflocal = Definition({ state: State =>
    state.stack match {
      case (name: String) :: (definition: Quotation) :: rest => state.withWord(name, PureDefinition(definition)).withStack(rest)
      case _ => throw new IllegalArgumentException( //
        "Expected stack to end in string quotation but cannot apply to ${state.stackStr}")
    }
  })

  val apply = Definition({ state: State =>
    state.stack match {
      case Quotation(particles) :: rest => state.withStack(rest).pushToCallstack(particles)
      case _ => throw new IllegalArgumentException( //
        "Expected stack to end in quotation but cannot apply to ${state.stackStr}")
    }
  })

  val applyToVar = PureDefinition("""
      [
        [
          "__apply_var" get get
          "__apply_func" get apply
          "__apply_var" get let
        ] swap "__apply_func" with-var
      ] swap "__apply_var" with-var
      """)
}