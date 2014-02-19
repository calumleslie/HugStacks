package com.github.calumleslie.hugs.stacks

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import scala.collection.script.Message
import org.scalatest.matchers.ShouldMatchers
import scala.collection.Seq
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.Str
import com.github.calumleslie.hugs.stacks.lang.Word
import com.github.calumleslie.hugs.stacks.lang.Bool
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FixNum

@RunWith(classOf[JUnitRunner])
class ParserSpec extends FunSpec with ShouldMatchers {

  describe("The stacks parser") {
    val parser = new Parser

    it("should parse strings") {
      parser.parse("\"string\" \"another\"") shouldBe (Seq(Str("string"), Str("another")))
    }

    it("should parse bools") {
      parser.parse("t f") shouldBe (Seq(Bool(true), Bool(false)))
    }

    it("should parse words") {
      parser.parse("true foo bar a + - %") shouldBe (Seq(Word("true"), Word("foo"), Word("bar"), Word("a"), Word("+"), Word("-"), Word("%")))
    }

    it("should parse fixnums") {
      parser.parse("123456 654321") shouldBe (Seq(FixNum(123456), FixNum(654321)))
    }

    it("should parse various types of floats") {
      parser.parse("0.5 .5 1.5") shouldBe (Seq(FloatNum(0.5), FloatNum(0.5), FloatNum(1.5)))
    }

    it("should parse all types without nesting") {
      parser.parse("123 1. .5 1.5 \"hello\" hello t f") shouldBe (Seq(
        FixNum(123), FloatNum(1.0), FloatNum(0.5), FloatNum(1.5), Str("hello"), Word("hello"), Bool(true), Bool(false)))
    }
  }
}
