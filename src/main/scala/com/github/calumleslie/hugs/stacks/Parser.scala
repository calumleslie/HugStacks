package com.github.calumleslie.hugs.stacks

import scala.util.parsing.combinator.RegexParsers
import com.github.calumleslie.hugs.stacks.lang.Particle
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FixNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import com.github.calumleslie.hugs.stacks.lang.FloatNum
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.combinator.JavaTokenParsers
import com.github.calumleslie.hugs.stacks.lang.Str
import com.github.calumleslie.hugs.stacks.lang.Quotation
import scala.collection._
import com.github.calumleslie.hugs.stacks.lang.Bool
import com.github.calumleslie.hugs.stacks.lang.Word
import scala.util.parsing.combinator.PackratParsers
import com.github.calumleslie.hugs.stacks.lang.Quotation

class Parser {
  def parse(line: String) = {
    immutable.Seq(ParserImpl.parseAll(ParserImpl.sentence, line).get: _*)
  }
  //ParserImpl.parseAll(ParserImpl.message, line).get

  private object ParserImpl extends JavaTokenParsers with PackratParsers {

    lazy val sentence: PackratParser[Seq[Particle]] = repsep(particle, whiteSpace)

    // Use of "word" here is a bit gnarly
    lazy val particle: PackratParser[Particle] = floatNum | fixNum | str | word | boolean | quotation | failure("Did not recognise particle")

    lazy val fixNum: PackratParser[FixNum] = """[1-9][0-9]*""".r ^^ {
      case numStr => FixNum(numStr.toInt)
    }

    lazy val floatNum: PackratParser[FloatNum] = """([1-9][0-9]*\.[0-9]*)|(0\.[0-9]*)|(\.[0-9]+)""".r ^^ {
      case numStr => FloatNum(numStr.toDouble)
    }

    lazy val str: PackratParser[Str] = stringLiteral ^^ { case s => Str(s.substring(1, s.length() - 1)) } // TODO: Apply escapes :'(

    // Don't ask why I'm doing this with a regex
    lazy val boolean: PackratParser[Bool] = """[tf]""".r ^^ { case s => Bool.fromStr(s) }

    // To "simplify parsing", words are 2+ characters
    lazy val word: PackratParser[Word] = """([^\s"]{2,})""".r ^^ { case name => Word(name) }

    lazy val quotation: PackratParser[Quotation] = "[" ~> sentence <~ "]" ^^ { case content => Quotation(immutable.Seq(content: _*)) }

    override val skipWhitespace = false
  }
}