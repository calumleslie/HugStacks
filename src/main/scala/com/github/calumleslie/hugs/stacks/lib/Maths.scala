package com.github.calumleslie.hugs.stacks.lib

import com.github.calumleslie.hugs.stacks.Definition
import com.github.calumleslie.hugs.stacks.State
import com.github.calumleslie.hugs.stacks.PureDefinition
import scala.collection.immutable.SortedMap
import scala.util.Random

object Maths {
  // Maybe I should be using a macro for some of this!

  private[this] val random = new Random()

  lazy val dictionary = SortedMap(
    "+" -> this.+,
    "-" -> this.-,
    "*" -> this.*,
    "/" -> this./,
    "floor" -> floor,
    ">" -> this.>,
    "<" -> this.<,
    "--" -> this.--,
    "++" -> this.++,
    "random-integer" -> this.randomInteger)

  val + = Definition("Consumes two numbers, pushes their sum", { state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x + y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x + y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x + y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x + y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val - = Definition("Consumes two numers, pushes the result of substracting the second from the first", { state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x - y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x - y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x - y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x - y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val * = Definition("Consumes two numbers, pushes their product", { state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x * y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x * y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x * y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x * y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val / = Definition("Consumes two numbers, pushes the result of dividing the second by the first", { state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x / y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x / y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x / y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x / y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val floor = Definition("Consumes a number, pushes its floor as an integer", { state: State =>
    state.stack match {
      case (x: Double) :: rest => state.withStack(x.floor.toLong :: rest)
      case (x: Long) :: rest => state
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val > = Definition("""
      Consumes two numbers, pushes t if the first is greater than the second, f otherwise
      """, { state: State =>
    state.stack match {
      case (y: Long) :: (x: Long) :: rest => state.withStack((x > y) :: rest)
      case (y: Double) :: (x: Double) :: rest => state.withStack((x > y) :: rest)
      case (y: Long) :: (x: Double) :: rest => state.withStack((x > y) :: rest)
      case (y: Double) :: (x: Long) :: rest => state.withStack((x > y) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val randomInteger = Definition("""
      Consumes an integer 'n', pushes an integer in the range [0,n). n must be <= Integer.MAX_VALUE.
      """, { state: State =>
    state.stack match {
      case (n: Long) :: rest => state.withStack(random.nextInt(n.toInt) :: rest)
      case _ => throw new IllegalArgumentException(s"Can't apply word to stack ${state.stack}")
    }
  })

  val -- = PureDefinition("Consumes a number, pushes a value 1 lower", "1 -")
  val ++ = PureDefinition("Consumes a number, pushes a value 1 higher", "1 +")

  val < = PureDefinition("""
      Consumes two numbers, pushes t if the first is lesser than the second, f otherwise
      """, """
      [
        [
          "x" get "y" get =
          "x" get "y" get >
          | !
        ] swap "x" with-var
      ] swap "y" with-var
      """)
}