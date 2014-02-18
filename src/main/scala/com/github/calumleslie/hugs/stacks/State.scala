package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Particle
import scala.collection.immutable.Map
import com.github.calumleslie.hugs.stacks.lang.Word

class State(val stack: List[Particle], val callStack: List[List[Particle]], val dictionary: Map[String, Definition]) {

  def step: Option[State] = callStack match {
    case Nil => None
    case Nil :: higher => Some(new State(stack, higher, dictionary))
    case (first :: rest) :: higher => Some(exec(first, new State(stack, rest :: higher, dictionary)))
  }

  def exec(particle: Particle, amendedState: State): State = particle match {
    case Word(name) => dictionary(name)(amendedState)
    case _ => amendedState.push(particle)
  }

  def withStack(newStack: List[Particle]) = new State(newStack, callStack, dictionary)

  def push(particle: Particle) = new State(particle :: stack, callStack, dictionary)

  override def toString() = s"stack($stack), callStack($callStack), dictionary(${dictionary.keys})"
}

object State {
  def apply(sentence: Seq[Particle], dictionary: Map[String, Definition]): State = {
    new State(Nil, List(sentence: _*) :: Nil, dictionary)
  }
}