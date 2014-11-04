package Digy

class Earth(hardness: Float, kind: Int, passable: Boolean) {
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
  
  def getKind = kind

}

object Earth {
  def apply(k: Int): Earth = {
    k match {
      case 1 => new Earth(0f, k, true)
      case 2 => new Earth(10f, k, false)
    }
  }
}