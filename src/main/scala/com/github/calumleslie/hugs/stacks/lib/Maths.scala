package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.PureDefinition

object Maths {
  // Maybe I should be using a macro for some of this!

  lazy val dictionary = Map(
    "+" -> this.+,
    "-" -> this.-,
    "*" -> this.*,
    "/" -> this./,
    "floor" -> floor,
    ">" -> this.>,
    "<" -> this.lt,
    "--" -> this.--,
    "++" -> this.++)

  val + = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x + y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x + y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x + y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x + y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val - = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x - y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x - y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x - y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x - y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val * = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x * y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x * y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x * y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x * y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val / = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x / y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x / y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x / y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x / y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val floor = Definition({ state: State =>
    state.stack match {
      case (x: Double) :: rest => state.withStack(x.floor.toLong :: rest)
      case (x: Long) :: rest => state
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val < = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x < y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x < y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x < y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x < y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val > = Definition({ state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x > y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x > y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x > y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x > y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val -- = PureDefinition("1 -")
  val ++ = PureDefinition("1 +")

  val lt = PureDefinition("dup2 = \"__equal\" let > \"__equal\" get | !")
}