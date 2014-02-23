package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Particle

trait Definition {
  def apply(state: State): State
  def documentation = "(no documentation)"
}

object Definition {
  def apply(transform: State => State) = new Definition {
    def apply(state: State) = transform(state)
    override def toString() = "(native)"
  }

  def apply(docs: String, transform: State => State) = new Definition {
    def apply(state: State) = transform(state)
    override def documentation = docs
    override def toString() = "(native)"
  }
}