package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.PureDefinition

object Functions {

  lazy val dictionary = Map("deflocal" -> deflocal)

  val deflocal = Definition({ state: State =>
    state.stack match {
      case (name: String) :: (definition: Quotation) :: rest => state.withWord(name, PureDefinition(definition))
      case _ => throw new IllegalArgumentException( //
        "Expected stack to end in string quotation but cannot apply to ${state.stackStr}")
    }
  })
}