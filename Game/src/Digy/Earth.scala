package Digy

class Earth(hardness: Float, location: Point2D, kind: Int, passable: Boolean) {
  val bSize = 20f
  def getBreakTime(): Float = {
    hardness * 10f
  }
 
  def isPassable(): Boolean = {
    passable
  }
  
  def getSize(): Float = {
    bSize
  }
  
}

object Earth {
  def apply(loc: Point2D , k: Int): Earth ={
    new Earth(10f, loc, k, true) 
  }
}