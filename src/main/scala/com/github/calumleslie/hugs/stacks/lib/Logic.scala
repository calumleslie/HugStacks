package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.PureDefinition

object Logic {

  lazy val dictionary = Map("!" -> this.!, "&" -> this.&, "|" -> this.|, "=" -> this.eq, "!=" -> this.neq)

  val ! = Definition({ state: State =>
    state.stack match {
      case (top: Boolean) :: rest => state.withStack(!top :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val & = Definition({ state: State =>
    state.stack match {
      case (left: Boolean) :: (right: Boolean) :: rest => state.withStack((left && right) :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val | = Definition({ state: State =>
    state.stack match {
      case (left: Boolean) :: (right: Boolean) :: rest => state.withStack((left || right) :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val eq = Definition({ state: State =>
    state.stack match {
      case y :: x :: rest => state.withStack((x == y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val neq = PureDefinition("= !")
}