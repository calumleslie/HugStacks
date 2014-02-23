package com.github.calumleslie.hugs.stacks

package object lib {
  val complete =
    Control.dictionary ++
      Functions.dictionary ++
      Maths.dictionary ++
      Lists.dictionary ++
      Logic.dictionary ++
      StackManip.dictionary ++
      Strings.dictionary ++
      Variables.dictionary
}