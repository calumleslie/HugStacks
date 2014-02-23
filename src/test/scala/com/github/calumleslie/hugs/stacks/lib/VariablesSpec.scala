package com.github.calumleslie.hugs.stacks.lib

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.calumleslie.hugs.stacks.Parser
import com.github.calumleslie.hugs.stacks.State
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class VariablesSpec extends FunSpec with ShouldMatchers {
  describe("with-var") {

    it("should allow getting value within block without leaving any detritus") {
      val result = evalOf("""
          [ "test" get ] 5 "test" with-var
          """)

      result.vars shouldBe (Map.empty)
      result.stack shouldBe (5 :: Nil)
    }
    
    it("should handle nested scopes") {
      val result = evalOf( """
          [ 
            [ 
              [ "test" get ] 1 "test" with-var
              "test" get
            ] 2 "test" with-var
          "test" get
          ] 3 "test" with-var
          """)
          
      result.vars shouldBe (Map.empty)
      result.stack shouldBe (3 :: 2 :: 1 :: Nil)
    }

    def evalOf(code: String) = {
      val program = Parser.parse(code)

      val initial = State(program, complete)

      println(initial.trace.map(_.toShortString()).mkString("\n"))

      initial.eval
    }
  }
}