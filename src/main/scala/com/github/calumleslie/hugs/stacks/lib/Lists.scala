package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.PureDefinition

object Lists {

  lazy val dictionary = Map("get-index" -> getIndex, "empty-list" -> emptyList, "append" -> append, "append-to-var" -> appendToVar)

  val getIndex = Definition({ state: State =>
    state.stack match {
      case (i: Long) :: (seq: Seq[Any]) :: rest => state.withStack(seq(i.toInt) :: rest)
      case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val emptyList = Definition(_.push(Nil))

  val append = Definition({ state: State =>
    state.stack match {
      case value :: (list: Seq[Any]) :: rest => state.withStack((list :+ value) :: rest)
      case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val appendToVar = PureDefinition("""
      [ swap append ] swap apply-to-var
      """)
}