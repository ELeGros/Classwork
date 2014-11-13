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
            nl = nl + dir*1/10
//            println("DEBUG:   Proposed new location: " + nl)
//            println("DEBUG:   Going to: " + fLoc)
            var theta = 0.0
            while(hit<0 && theta < 2*math.Pi) {
              println("The block at: " + "(" + ((nl.x + math.cos(theta)*bounds._1/2) + ", " + ((nl.y + math.sin(theta)*bounds._2/2)) + ")" + " passable? : " + wData((nl.x + math.cos(theta)).toInt)((nl.y + math.sin(theta)).toInt).isPassable()))
              if(!(wData((nl.x + math.cos(theta)*bounds._1/2).toInt)((nl.y + math.sin(theta)*bounds._2/2).toInt).isPassable())){ 
                hit = theta
                g = true
                println("Hit at: (" + ((nl.x + math.cos(theta)*bounds._1/2).toInt + ", " + ((nl.y + math.sin(theta)*bounds._2/2).toInt) + ")"))
              }
              else {
                theta+=.5
                println("No hit at: (" + ((nl.x + math.cos(theta)*bounds._1/2).toInt + ", " + ((nl.y + math.sin(theta)*bounds._2/2).toInt) + ")"))
              }
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