package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Particle
import scala.collection.immutable.Map
import com.github.calumleslie.hugs.stacks.lang.Word
import com.github.calumleslie.hugs.stacks.lang.Quotation
import com.github.calumleslie.hugs.stacks.lang.Bool
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.Str
import scala.annotation.tailrec
import com.typesafe.scalalogging.slf4j.Logging

class State(val stack: List[Any], val callStack: List[List[Particle]], val dictionary: Map[String, Definition], val vars: Map[String, Any]) {

  def eval = State.eval(this)
  def trace = State.trace(this)

  def step: StepResult = try {
    callStack match {
      case Nil => Finished
      case Nil :: higher => Continue(State(stack, higher, dictionary, vars))
      case (first :: rest) :: higher => Continue(exec(first, State(stack, rest :: higher, dictionary, vars)))
    }
  } catch {
    case e: Exception => Error(e)
  }

  def exec(particle: Particle, amendedState: State): State = particle match {
    case Word(name) => dictionary.get(name) match {
      case Some(defn) => defn(amendedState)
      case None => throw new IllegalArgumentException(s"Word $name is not known")
    }
    case q @ Quotation(_) => amendedState.push(q)
    case Bool(value) => amendedState.push(value)
    case FixNum(value) => amendedState.push(value)
    case FloatNum(value) => amendedState.push(value)
    case Str(value) => amendedState.push(value)
  }

  def withStack(newStack: List[Any]) = State(newStack, callStack, dictionary, vars)

  def pushToCallstack(newTop: List[Particle]) = withCallstack(newTop :: callStack)

  def pushToCallstack(newTop: Particle*): State = pushToCallstack(List(newTop: _*))

  def withCallstack(newCallStack: List[List[Particle]]) = State(stack, newCallStack, dictionary, vars)

  def withDictionary(newDictionary: Map[String, Definition]) = State(stack, callStack, newDictionary, vars)

  def withVar(name: String, value: Any) = State(stack, callStack, dictionary, vars + (name -> value))

  def withoutVar(name: String) = State(stack, callStack, dictionary, vars - name)

  def withWord(name: String, value: Definition) = withDictionary(dictionary + (name -> value))

  def push(newTop: Any) = withStack(newTop :: stack)

  def stackStr = stack.reverse.mkString(" ")

  def traceStr = callStack.map(_.mkString(" ")).mkString(" ")

  def varStr = vars.mkString(", ")

  override def toString() = s"stack($stackStr), callStack($callStack), dictionary(${dictionary})"

  def toShortString() = s"$stackStr <> $traceStr <> $varStr"

  def toHtmlRow() = <tr><td>{ stackStr }</td><td>{ traceStr }</td><td>{ varStr }</td></tr>
}

object State extends Logging {
  def apply(stack: List[Any], callStack: List[List[Particle]], dictionary: Map[String, Definition], vars: Map[String, Any]) = {
    new State(stack, callStack.filterNot(_.isEmpty), dictionary, vars)
  }

  def apply(sentence: Seq[Particle], dictionary: Map[String, Definition]): State = {
    new State(Nil, List(sentence: _*) :: Nil, dictionary, Map.empty)
  }

  @tailrec
  def eval(state: State): State = state.step match {
    case Finished => state
    case Error(e) => throw new Exception(s"Encountered an error at state $state", e)
    case Continue(next) => {
      logger.debug(next.toShortString())
      eval(next)
    }
  }

  def trace(state: State): List[State] = trace(state, Nil)

  @tailrec
  private def trace(state: State, before: List[State]): List[State] = state.step match {
    case Finished => (state :: before).reverse
    case Error(e) => throw new TraceException((state :: before).reverse, e)
    case Continue(next) => trace(next, state :: before)
  }

  def traceToHtml(states: List[State]) = <table>{ states.map(_.toHtmlRow) }</table>
}