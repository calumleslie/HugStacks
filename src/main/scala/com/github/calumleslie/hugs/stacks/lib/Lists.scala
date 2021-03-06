package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.PureDefinition
import scala.collection.immutable.SortedMap

object Lists {

  lazy val dictionary = SortedMap(
      "get-index" -> getIndex,
      "empty-list" -> emptyList,
      "append" -> append, 
      "append-to-var" -> appendToVar,
      "make-list" -> makeList, 
      "size" -> size)

  val getIndex = Definition("""
      Consumes a list and an integer, pushes the i-th value from the list. 
      """, { state: State =>
    state.stack match {
      case (i: Long) :: (seq: Seq[Any]) :: rest => state.withStack(seq(i.toInt) :: rest)
      case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
    }
  })

  val emptyList = Definition("Pushes and empty list", _.push(Nil))

  val append = Definition("""
      Consumes a list and a value, pushes a list with the value appended.
      """, { state: State =>
    state.stack match {
      case value :: (list: Seq[Any]) :: rest => state.withStack((list :+ value) :: rest)
      case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
    }
  })
  
  val makeList = Definition("""
      Consumes a quotation filled with non-word values and pushes an equivalent list.
      """, { state: State =>
        state.stack match {
          case Quotation(values) :: rest => state.withStack(values.map(_.toPrimitive) :: rest)
          case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
        }
      } )
      
  val size = Definition("""
      Consumes a list, pushes the number of elements in the list.
      """, { state: State =>
        state.stack match {
          case (list: List[Any]) :: rest => state.withStack(list.size.toLong :: rest)
          case _ => throw new UnsupportedOperationException(s"Cannot apply to stack ${state.stackStr}")
        }
      } )

  val appendToVar = PureDefinition("Consumes a value and a string, appends the value to the list named by the string, and stores the result in the same var.", """
      [ swap append ] swap apply-to-var
      """)
}