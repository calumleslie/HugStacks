package com.github.calumleslie.hugs.stacks.lib

import java.util.Dictionary
import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State

object Strings {

  lazy val dictionary = Map("string-append" -> stringAppend)

  val stringAppend = Definition("Consumes two strings, pushes their concatenation", { state: State =>
    state.stack match {
      case (y: String) :: (x: String) :: rest => state.withStack(x + y :: rest)
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })
}