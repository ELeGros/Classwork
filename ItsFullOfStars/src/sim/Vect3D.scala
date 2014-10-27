package sim

case class Vect3D(x: Double, y: Double, z: Double) {
   def +(v:Vect3D): Vect3D = Vect3D(x + v.x, y + v.y, z + v.z)
   def -(v:Vect3D): Vect3D = Vect3D(x - v.x, y - v.y, z - v.z)
   def *(s: Double): Vect3D = Vect3D(x*s, y*s, z*s)
   def /(s: Double): Vect3D = Vect3D(x/s, y/s, z/s)
   def magnitude:Double = math.sqrt(x*x + y*y + z*z)
   def dot(v : Vect3D): Double = x * v.x + y * v.y + z * v.z
}