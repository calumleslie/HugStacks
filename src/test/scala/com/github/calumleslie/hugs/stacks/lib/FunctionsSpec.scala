package com.github.calumleslie.hugs.stacks.lib

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.calumleslie.hugs.stacks.Parser
import com.github.calumleslie.hugs.stacks.State
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunctionsSpec extends FunSpec with ShouldMatchers with ProgramSpec {
  describe("apply") {

    it("should apply the quotation immediately") {
      val result = evalOf("""
          2 [ 2 + ] apply
          """)

      result.vars shouldBe (Map.empty)
      result.stack shouldBe (4 :: Nil)
    }
  }

  describe("apply-to-var") {
    it("should apply the quotation to the supplied variable") {
      val result = evalOf("""
          1 "counter" let
          [ ++ ] "counter" apply-to-var
          """)

      result.vars shouldBe Map("counter" -> 2L)
      result.stack shouldBe (Nil)
    }

    it("should work when nested") {
      val result = evalOf("""
          1 "counter1" let
          1 "counter2" let
          [
            [ ++ ] "counter2" apply-to-var
            "counter2" get +
          ] "counter1" apply-to-var
          """)

      result.vars shouldBe Map("counter1" -> 3L, "counter2" -> 2L)
      result.stack shouldBe (Nil)
    }
  }
}