package Digy

abstract class Entity(val location: Point2D, val bounds: (Float, Float), val direction: Vect2D, val grounded: Boolean) extends Serializable {

  def makeCopy(location: Point2D, direction: Vect2D, grounded: Boolean): Entity
  def gravity(dir: Vect2D, gravity: Vect2D): Vect2D = {
    if (grounded != true) {
      dir + gravity
    } else {
      dir
    }
  }

  def update(wData: Vector[Vector[Earth]]): Entity = {
    println(grounded)
    if (!grounded) {
      var nl = location
      var g = false
      val dir = gravity(direction, new Vect2D(0, -.3))
      val fLoc = location + dir
      var hit = -10.0
      while (hit < 0 && (math.abs(((nl - fLoc)).y) > .05 || math.abs((nl - fLoc).x) > .05)) {
        var theta = 0.0
        while (hit < 0 && theta < 2 * math.Pi) {
          println("infinite loop?")
          if (!(wData((nl.x + math.cos(theta) * bounds._1 / 2).toInt)((nl.y + math.sin(theta) * bounds._2 / 2).toInt).isPassable())) {
            hit = theta
            g = true
          } else {
            theta += .5
          }
          println(hit + "   " + theta)
        }        
        println("left inner loop, hit is:" + hit)
        if (hit < 0) nl = nl + dir * 1 / 10
      }
      println("left the loop")
      makeCopy(nl, dir, g)
    } else makeCopy(location + direction, direction, true)
  }

}


