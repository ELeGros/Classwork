package sim

case class Point3D(x: Double, y: Double, z: Double) {
  def +(v:Vect3D): Point3D = Point3D(x + v.x, y + v.y, z + v.z)
  def -(v:Vect3D): Point3D = Point3D(x - v.x, y - v.y, z - v.z)
  def -(p: Point3D): Vect3D = Vect3D(x - p.x, y - p.y, z - p.z)
}