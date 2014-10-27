package basics

object Mandelbrot {
  val maxIters = 100

  def mandelIter(z: Complex, c: Complex): Complex = z * z + c
/*
  def mandelCount(x: Complex): Int = {
    var z = Complex(0, 0)
    var cnt = 0
    while (cnt < maxIters && z.magnitude < 4) {
      z = mandelIter(z, c)
      cnt += 1
    }
    cnt
  }
*/
  def main(args: Array[String]): Unit = {

  }
}