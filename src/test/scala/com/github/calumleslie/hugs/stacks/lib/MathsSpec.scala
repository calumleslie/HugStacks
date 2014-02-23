package com.github.calumleslie.hugs.stacks.lib

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MathsSpec extends FunSpec with ShouldMatchers with ProgramSpec {
  describe("<") {

    it("should be false when equal") {
      val result = evalOf("2 2 <")

      result.vars shouldBe (Map.empty)
      result.stack shouldBe (false :: Nil)
    }
    
    it("should be false when greater") {
      val result = evalOf("2 1 <")

      result.vars shouldBe (Map.empty)
      result.stack shouldBe (false :: Nil)
    }
    
    it("should be true when lesser") {
      val result = evalOf("2 3 <")

      result.vars shouldBe (Map.empty)
      result.stack shouldBe (true :: Nil)
    }
  }
}