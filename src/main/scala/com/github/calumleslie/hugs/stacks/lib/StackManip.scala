package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State

object StackManip {

  lazy val dictionary = Map("noop" -> noop, "dup" -> dup, "dup2" -> dup2, "swap" -> swap)

  val noop = Definition(identity[State])
  val dup = Definition({ state: State =>
    state.stack match {
      case Nil => state
      case top :: rest => state.push(top)
    }
  })
  val dup2 = Definition({ state: State =>
    state.stack match {
      case first :: second :: rest => state.withStack(first :: second :: state.stack)
      case _ => state
    }
  })
  val swap = Definition({ state: State =>
    state.stack match {
      case first :: next :: rest => state.withStack(next :: first :: rest)
      case _ => state
    }
  })

}