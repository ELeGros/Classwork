package Digy

class Player(location: Point2D, bounds: (Float, Float), direction: Vect2D, grounded: Boolean) extends Entity(location, bounds, direction, grounded){
   def makeCopy (location: Point2D, direction: Vect2D, grounded: Boolean):Entity = if(grounded) Player(location, bounds, Vect2D(0,0),grounded) else Player(location, bounds, direction, grounded)
  def upPressed(): Player = {
    if (grounded) Player(location+Vect2D(0,.8), bounds, direction + Vect2D(0, 3), false)
    else this
  }
  def downPressed():Player = {
    this
  }
  
  def leftPressed(): Player = {
    println("left was pressed")
    if(grounded) {Player(location+Vect2D(0,2), bounds, direction + Vect2D(-1,0), false)} 
    else this
  }
  
  def rightPressed(): Player = {
    if(grounded) {Player(location+Vect2D(0,2), bounds, direction + Vect2D(1,0), false)}
    else this
  }
}

object Player {
  def apply(location: Point2D, bounds: (Float, Float), direction: Vect2D, grounded: Boolean): Player = {
    new Player(location, bounds, direction, grounded)
  }
}