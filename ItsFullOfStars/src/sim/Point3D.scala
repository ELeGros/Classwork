package sim

case class Point3D(x: Double, y: Double, z: Double) {
  def +(v: Vect3D): Point3D = Point3D(x + v.x, y + v.y, z + v.z)
  def +(p: Point3D): Point3D = Point3D(x + p.x, y + p.y, z + p.z)
  def -(v: Vect3D): Point3D = Point3D(x - v.x, y - v.y, z - v.z)
  def -(p: Point3D): Vect3D = Vect3D(x - p.x, y - p.y, z - p.z)
  def *(c: Double): Point3D = Point3D(c * x, c * y, c * z)
  def /(c: Double): Point3D = Point3D(x/c, y/c, z/c)
  def apply(i: Int): Double = i match {
    case 0 => x
    case 1 => y
    case 2 => z
  }
}