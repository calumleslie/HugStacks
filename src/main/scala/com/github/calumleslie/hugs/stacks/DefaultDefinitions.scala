package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FixNum

object DefaultDefinitions {
  val noop = Definition(identity[State])
  val dup = Definition({ state: State =>
    state.stack match {
      case Nil => state
      case top :: rest => state.push(top)
    }
  })
  val add = Definition({ state: State =>
    state.stack match {
      case FixNum(x) :: FixNum(y) :: rest => state.withStack(FixNum(x + y) :: rest)
      case _ => throw new IllegalArgumentException("Can't apply add to stack ${state.stack}")
    }
  })
}