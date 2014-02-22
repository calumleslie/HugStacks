package com.github.calumleslie.hugs.stacks

trait StepResult
case class Continue(state: State) extends StepResult
case object Finished extends StepResult
case class Error(err: Exception) extends StepResult