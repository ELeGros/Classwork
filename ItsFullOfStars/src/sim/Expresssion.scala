package basics

import scala.util.parsing.combinator.JavaTokenParsers
 
class Expression(e: String) extends JavaTokenParsers {
  trait Node extends ((Map[String, Double]) => Double)

  class NumberNode(label: Double) extends Node {
    def apply(vars: Map[String, Double]): Double = {
      label
    }
  }

  class VarNode(label: String) extends Node {
    def apply(vars: Map[String, Double]): Double = {
      vars(label)
    }
  }

  class OpNode(first: Node, rest: List[~[String, Node]]) extends Node {
    def apply(vars: Map[String, Double]): Double = {
      var ret = first(vars)
      for (op ~ n <- rest) {
        op match {
          case "+" => ret += n(vars)
          case "-" => ret -= n(vars)
          case "*" => ret *= n(vars)
          case "/" => ret /= n(vars)
        }
      }
      ret
    }
  }
  val root = parseAll(expr, e).get
  def expr: Parser[Node] = term ~ rep("+" ~ term | "-" ~ term) ^^ { case t ~ lst => new OpNode(t, lst) }
  def term: Parser[Node] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ { case f ~ lst => new OpNode(f, lst) }
  def factor: Parser[Node] = floatingPointNumber ^^ (s => new NumberNode(s.toDouble)) |
    "(" ~> expr <~ ")" | ident ^^ (s => new VarNode(s))
}

object Expression extends App {
  val whatever = new Expression("4*(15*i*x+-7)/21+5")
  println(whatever.root(Map("i" -> 1, "x" -> 2)))
}

