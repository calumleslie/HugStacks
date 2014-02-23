package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Word
import com.github.calumleslie.hugs.stacks.lang.Quotation
import scala.collection.immutable.SortedMap

object Control {

  lazy val dictionary = SortedMap("if" -> if_, "loop" -> loop)

  val if_ = Definition("""
      Consumes boolean and two quotations, applies the first quotation if the boolean is t, the second otherwise.
      """, { state: State =>
    state.stack match {
      case _ :: (ifTrue: Quotation) :: true :: rest => state.withStack(rest).pushToCallstack(ifTrue.particles)
      case (ifFalse: Quotation) :: _ :: false :: rest => state.withStack(rest).pushToCallstack(ifFalse.particles)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stackStr}")
    }
  })

  def loop = Definition("""
      Consumes a quotation which should leave a boolean on the stack and applies that quotation until it leaves 't' on the stack. Consumes the resulting booleans. 
      """, { state: State =>
    state.stack match {
      case (body: Quotation) :: rest => state.withStack(rest). //
        pushToCallstack(List(Quotation(body, Word("loop")), Quotation(Nil), Word("if"))). // If output was true, run body again, else do nothing
        pushToCallstack(body.particles)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stackStr}")
    }
  })
}