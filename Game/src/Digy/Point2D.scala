package Digy

case class Point2D(x: Double, y: Double) {
  def +(v:Vect2D): Point2D = Point2D(x + v.x, y + v.y)
  def -(v:Vect2D): Point2D = Point2D(x - v.x, y - v.y) 
  def -(p: Point2D): Vect2D = Vect2D(x - p.x, y - p.y)
}