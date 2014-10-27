package Digy

class Entity(val location: Point2D, val bounds: (Float, Float), val direction: Vect2D, val grounded: Boolean) {

  def update(wData: Map[Point2D, Earth]): Entity = {
    if(!grounded){
    val bot = Point2D(location.x ,location.y - bounds._2/2)
    val dir = gravity(direction, Vect2D(0,-.5))
    val uPos = location + dir
    var fPos = uPos
    var g = false
    for(i <- 1 to ((uPos.y).toInt-(math.ceil(bot.y).toInt))){
      if(!wData(Point2D(location.x.toInt.toDouble, (math.ceil(location.y) + i - 1).toDouble)).isPassable && fPos == uPos) {
        fPos = Point2D(location.x, (math.ceil(location.y) + i - 1).toDouble) 
        g = true
      }
    }
    Entity(fPos, bounds, dir, g)
    }
    else this
  }

  def gravity(dir: Vect2D, gravity: Vect2D): Vect2D = {
    if (grounded != true) {
      dir + gravity
    } else {
      dir
    }
  }
}

object Entity {
  def apply(loc: Point2D, boundBox: (Float, Float), dir: Vect2D, grnd: Boolean): Entity = {
    new Entity(loc, boundBox, dir, grnd)
  }
}