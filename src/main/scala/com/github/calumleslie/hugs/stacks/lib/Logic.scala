package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.PureDefinition
import scala.collection.immutable.SortedMap

object Logic {

  lazy val dictionary = SortedMap("!" -> this.!, "&" -> this.&, "|" -> this.|, "=" -> this.eq, "!=" -> this.neq)

  val ! = Definition("Consumes a boolean, pushes its negation.", { state: State =>
    state.stack match {
      case (top: Boolean) :: rest => state.withStack(!top :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val & = Definition("Consumes two booleans, pushes the AND of their values.", { state: State =>
    state.stack match {
      case (left: Boolean) :: (right: Boolean) :: rest => state.withStack((left && right) :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val | = Definition("Consumes two booleans, pushes the OR of their values.", { state: State =>
    state.stack match {
      case (left: Boolean) :: (right: Boolean) :: rest => state.withStack((left || right) :: rest)
      case _ => throw new IllegalArgumentException("Cannot apply to ${state.stackStr}")
    }
  })

  val eq = Definition("Consumes two values, pushes the result of comparing them for equality.", { state: State =>
    state.stack match {
      case y :: x :: rest => state.withStack((x == y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val neq = PureDefinition("Consumes two values, pushes the negation of the result of comparing them for equality", "= !")
}