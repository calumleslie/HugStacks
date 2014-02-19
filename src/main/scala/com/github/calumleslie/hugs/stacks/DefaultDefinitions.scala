package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FixNum

object DefaultDefinitions {
  val noop = Definition({ state: State => state })
  val dup = Definition({ state: State =>
    state.stack match {
      case Nil => state
      case top :: rest => state.push(top)
    }
  })
  val add = Definition({ state: State =>
    state.stack match {
      case (x: Long) :: (y: Long) :: rest => state.withStack((x + y) :: rest)
      case (x: Double) :: (y: Double) :: rest => state.withStack((x + y) :: rest)
      case (x: Long) :: (y: Double) :: rest => state.withStack((x + y) :: rest)
      case (x: Double) :: (y: Long) :: rest => state.withStack((x + y) :: rest)
      case (x: Long) :: rest => throw new IllegalArgumentException(s"A Can't apply add to stack ${state.stack}")
      case _ => throw new IllegalArgumentException(s"Can't apply add to stack ${state.stack}")
    }
  })
}