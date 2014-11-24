package Digy

class Player(location: Point2D, bounds: (Float, Float), direction: Vect2D, grounded: Boolean) extends Entity(location, bounds, direction, grounded){
   def makeCopy (location: Point2D, direction: Vect2D, grounded: Boolean):Entity = Player(location, bounds, direction,grounded)
  def upPressed(): Player = {
    if (grounded) Player(location+Vect2D(0,1), bounds, direction + Vect2D(0, 3), false)
    else this
  }
  def downPressed():Player = {
    this
  }
  
  def leftPressed(): Player = {
    if(grounded) {???} 
    else this
  }
  
  def rightPressed(): Player = {
    if(grounded) {???}
    else this
  }
}

object Player {
  def apply(location: Point2D, bounds: (Float, Float), direction: Vect2D, grounded: Boolean): Player = {
    new Player(location, bounds, direction, grounded)
  }
}