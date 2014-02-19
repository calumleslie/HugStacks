package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Particle
import scala.collection.immutable.Map
import com.github.calumleslie.hugs.stacks.lang.Word
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Bool
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.Str

class State(val stack: List[Any], val callStack: List[List[Particle]], val dictionary: Map[String, Definition]) {

  def step: Option[State] = callStack match {
    case Nil => None
    case Nil :: higher => Some(new State(stack, higher, dictionary))
    case (first :: rest) :: higher => Some(exec(first, new State(stack, rest :: higher, dictionary)))
  }

  def exec(particle: Particle, amendedState: State): State = particle match {
    case Word(name) => dictionary(name)(amendedState)
    case q @ Quotation(particles) => amendedState.push(q)
    case Bool(value) => amendedState.push(value)
    case FixNum(value) => amendedState.push(value)
    case FloatNum(value) => amendedState.push(value)
    case Str(value) => amendedState.push(value)
  }

  def withStack(newStack: List[Any]) = new State(newStack, callStack, dictionary)

  def push(newTop: Any) = withStack(newTop :: stack)

  override def toString() = s"stack($stack), callStack($callStack), dictionary(${dictionary.keys})"
}

object State {
  def apply(sentence: Seq[Particle], dictionary: Map[String, Definition]): State = {
    new State(Nil, List(sentence: _*) :: Nil, dictionary)
  }
}