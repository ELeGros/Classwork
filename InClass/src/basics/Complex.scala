package basics

class Complex(val real: Double, val imag: Double) {
  def +(that: Complex): Complex = Complex(real + that.real, imag + that.imag)
  def -(that: Complex): Complex = Complex(real - that.real, imag - that.imag)
  def *(that: Complex): Complex = {
    val realPart = real * that.real - imag * that.imag
    val imagPart = real * that.imag + imag * that.real
    Complex(realPart, imagPart)
  }
  def magnitude: Double = math.sqrt(real * real + imag * imag)

}

object Complex {
  def apply(real: Double, imag: Double) = new Complex(real, imag)
}