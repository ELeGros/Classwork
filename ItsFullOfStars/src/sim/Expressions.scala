package sim

class Expressions(s: String) {
  private val root = parse(s)
  def apply(x:Double): Double = root.eval(x)
  private trait Node {
    def eval(x: Double): Double
  }

  private class NumNode(d: Double) extends Node {
    def eval(x: Double): Double = d
  }

  private class VarNode extends Node {
    def eval(x: Double): Double = x
  }

  private class UnOpNode(f: Double => Double, r: Node) extends Node {
    def eval(x: Double): Double = f(r.eval(x))
  }
  private class BinOpNode(f: (Double, Double) => Double, left: Node, right: Node) extends Node {
    def eval(x: Double): Double = f(left.eval(x), right.eval(x))
  }
  private def parse(exp: String): Node = {
    var i = exp.length - 1
    var parenCount = 0
    var op = -1
    while (i > 0) {
      if (exp(i) == '(') parenCount += 1
      else if (exp(i) == ')') parenCount -= 1
      else if ((exp(i) == '+' || exp(i) == '-') && parenCount == 0) {
        op = i
        i = -1
      } else if ((exp(i) == '*' || exp(i) == '/') && parenCount == 0 && op == -1) {
        op = i
      }
      i -= 1
    }
    if(op == -1){
      if(exp(0) == '(') parse(exp.substring(1, exp.length - 1))
      else if(exp(0) == 'x') new VarNode
      else if(exp.startsWith("sin(")) new UnOpNode(math.sin(_), parse(exp.substring(4,exp.length-1)))
      else if(exp.startsWith("cos(")) new UnOpNode(math.cos(_), parse(exp.substring(4,exp.length-1)))
      else new NumNode(exp.toDouble)
    }
    else {
      val left = parse(exp.substring(0, op))
      val right = parse(exp.substring(op+1))
      exp(op) match {
        case '+' => new BinOpNode(_+_, left, right)
        case '-' => new BinOpNode(_-_, left, right)
        case '*' => new BinOpNode(_*_, left, right)
        case '/' => new BinOpNode(_/_, left, right)
      }
    }
  }
}

object Expressions extends App {
  val e = new Expressions("(3+5)*2-x")
  println(e(1))
}