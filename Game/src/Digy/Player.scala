package Digy

class Player(location: Point2D, bounds: (Float, Float), direction: Vect2D, grounded: Boolean) extends Entity(location, bounds, direction, grounded){
  def upPressed(): Player = {
    if (grounded) Player(location, bounds, direction + Vect2D(0, .5), false)
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