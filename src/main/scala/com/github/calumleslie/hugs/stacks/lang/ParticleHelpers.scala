package com.github.calumleslie.hugs.stacks.lang

object ParticleHelpers {
  implicit def stringToStringHelper(string: String) = new StringHelper(string)

  class StringHelper(val string: String) extends AnyVal {
    def word = Word(string)
    def str = Str(string)
  }
}