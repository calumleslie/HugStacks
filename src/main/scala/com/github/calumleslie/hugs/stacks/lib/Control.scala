package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Word
import com.github.calumleslie.hugs.stacks.lang.Quotation

object Control {

  lazy val dictionary = Map("if" -> if_, "loop" -> loop)

  val if_ = Definition({ state: State =>
    state.stack match {
      case _ :: (ifTrue: Quotation) :: true :: rest => state.withStack(rest).pushToCallstack(ifTrue.particles)
      case (ifFalse: Quotation) :: _ :: false :: rest => state.withStack(rest).pushToCallstack(ifFalse.particles)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stackStr}")
    }
  })

  def loop = Definition({ state: State =>
    state.stack match {
      case (body: Quotation) :: rest => state.withStack(rest). //
        pushToCallstack(List(Quotation(body, Word("loop")), Quotation(Nil), Word("if"))). // If output was true, run body again, else do nothing
        pushToCallstack(body.particles)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stackStr}")
    }
  })
}