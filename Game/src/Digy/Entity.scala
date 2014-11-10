package Digy

class Entity(val location: Point2D, val bounds: (Float, Float), val direction: Vect2D, val grounded: Boolean) {

   def gravity(dir: Vect2D, gravity: Vect2D): Vect2D = {
    if (grounded != true) {
      dir + gravity
    } else {
      dir
    }
  }
  
  def update(wData: Vector[Vector[Earth]]): Entity = {
    if (!grounded) {
          var nl = location
          var g = false
          val dir = gravity(direction, new Vect2D(0, -.3))
          val fLoc = location + dir
//          println("DEBUG:   Current location: " + location)
//          println("DEBUG:   Current velocity: " + dir)
          var hit = -10.0
          while(hit < 0 && ((nl - fLoc).y >.05 || (nl - fLoc).x >.05 )) {
            nl = nl + dir*1/640
 //           println("DEBUG:   Proposed new location: " + nl)
 //          println("DEBUG:   Going to: " + fLoc)
            var theta = 0.0
            while(hit<0 && theta < 2*math.Pi) {
              if(!(wData((location.x + math.cos(theta)).toInt)((location.y + math.sin(theta)).toInt).isPassable())){ 
                hit = theta
                g = true
              }
              theta+=.5
            }
          }     
      Entity(nl, bounds, dir, g)
    } else this
  }


}

object Entity {
  def apply(loc: Point2D, boundBox: (Float, Float), dir: Vect2D, grnd: Boolean): Entity = {
    new Entity(loc, boundBox, dir, grnd)
  }
}