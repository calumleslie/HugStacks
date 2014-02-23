package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import scala.collection.immutable.SortedMap

object StackManip {

  lazy val dictionary = SortedMap("noop" -> noop, "dup" -> dup, "dup2" -> dup2, "swap" -> swap)

  val noop = Definition("Does nothing", identity[State])
  val dup = Definition("Pushes a copy of the top of the stack, e.g. 1 2 dup -> 1 2 2", { state: State =>
    state.stack match {
      case Nil => state
      case top :: rest => state.push(top)
    }
  })
  val dup2 = Definition("""
      Pushes copies of the top two values on the stack onto the stack, e.g. 1 2 3 dup2 -> 1 2 3 2 3
      """, { state: State =>
    state.stack match {
      case first :: second :: rest => state.withStack(first :: second :: state.stack)
      case _ => state
    }
  })
  val swap = Definition("Swaps the top two values on the stack, e.g. 1 2 3 swap -> 1 3 2", { state: State =>
    state.stack match {
      case first :: next :: rest => state.withStack(next :: first :: rest)
      case _ => state
    }
  })

}