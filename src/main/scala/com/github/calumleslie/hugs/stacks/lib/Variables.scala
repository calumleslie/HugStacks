package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import java.util.UUID
import com.github.calumleslie.hugs.stacks.lang.ParticleHelpers._

object Variables {

  lazy val dictionary = Map("let" -> let, "get" -> get, "del" -> del, "with-var" -> withVar)

  val let = Definition("Consumes a value and a string, stores the value in the var named by the string", { state: State =>
    state.stack match {
      case (name: String) :: value :: rest => state.withVar(name, value).withStack(rest)
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val get = Definition("Consumes a string, pushes the value in the var named by the string", { state: State =>
    state.stack match {
      case (name: String) :: rest => state.vars.get(name) match {
        case None => throw new IllegalArgumentException(s"No var called $name available (available vars: ${state.vars.keys})")
        case Some(value) => state.withStack(value :: rest)
      }
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val del = Definition("Consumes a string, deletes the var named by the string", { state: State =>
    state.stack match {
      case (name: String) :: rest => state.withStack(rest).withoutVar(name)
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  // TODO: This is a tremendous hack.
  val withVar = Definition("""
      Consumes a quotation, a value, and a string. Sets the var named by the string to the value, applies the quotation,
      and restores the stack to the state it was before
      """, { state: State =>
    state.stack match {
      case (name: String) :: value :: (scoped: Quotation) :: rest => {

        val withNewStack = state.withStack(rest)

        val withScope = state.vars.get(name) match {
          case Some(existingValue) => {
            // We are going to occlude this variable. Hide it somewhere and return it later.
            val shadowName = s"__occluded name:${name},uniq:${UUID.randomUUID()}"

            withNewStack.
              withVar(shadowName, existingValue).
              withVar(name, value).
              pushToCallstack(
                shadowName.str, "get".word, // Get shadowed value back 
                shadowName.str, "del".word, // Delete shadow store 
                name.str, "let".word) // Restore shadowed value
          }
          case None => {
            withNewStack.
              withVar(name, value).
              pushToCallstack(name.str, "del".word)
          }
        }

        withScope.pushToCallstack(scoped.particles)
      }
      case _ => throw new IllegalArgumentException(s"Cannot apply to stack ${state.stackStr}")
    }
  })
}