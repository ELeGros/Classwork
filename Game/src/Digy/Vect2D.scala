package Digy

case class Vect2D(x: Double, y: Double) {
   def +(v:Vect2D): Vect2D = Vect2D(x + v.x, y + v.y)
   def -(v:Vect2D): Vect2D = Vect2D(x - v.x, y - v.y)
   def *(s: Double): Vect2D = Vect2D(x*s, y*s)
   def /(s: Double): Vect2D = Vect2D(x/s, y/s)
   def magnitude:Double = math.sqrt(x*x + y*y)
   def dot(v : Vect2D): Double = x * v.x + y * v.y
   override def toString(): String = "(" + x + ", " + y + ")"
}