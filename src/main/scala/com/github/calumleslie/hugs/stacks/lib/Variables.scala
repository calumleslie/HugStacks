package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State

object Variables {

  lazy val dictionary = Map("let" -> let, "get" -> get)

  val let = Definition({ state: State =>
    state.stack match {
      case (name: String) :: value :: rest => state.withVar(name, value).withStack(rest)
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val get = Definition({ state: State =>
    state.stack match {
      case (name: String) :: rest => state.vars.get(name) match {
        case None => throw new IllegalArgumentException(s"No var called $name available (available vars: ${state.vars.keys})")
        case Some(value) => state.withStack(value :: rest)
      }
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })
}