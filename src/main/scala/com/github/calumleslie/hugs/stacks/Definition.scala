package com.github.calumleslie.hugs.stacks

import com.github.calumleslie.hugs.stacks.lang.Particle

trait Definition {
  def apply(state: State): State
}

object Definition {
  def apply(transform: State => State) = new Definition {
    def apply(state: State) = transform(state)
  }
}