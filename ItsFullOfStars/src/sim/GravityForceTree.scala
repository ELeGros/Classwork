package sim

class GravityKDTree(initialParticles: Seq[Particle]) {
  private val parts = Array(initialParticles: _*)
  private val MAX_PARTS = 5
  private case class Node(ps: Seq[Particle], dim: Int, v: Double, left: Node, right: Node) {
    var cMass = 0.0
    var cPoint = Point3D(0, 0, 0)
    var xMin, xMax, yMin, yMax, zMin, zMax = 0.0
    def calcValues: Node = {
      if (ps.isEmpty) {
        cMass = left.cMass + right.cMass
        cPoint = (left.cPoint * left.cMass + right.cPoint * right.cMass) / cMass
        xMin = left.xMin min right.xMin
        xMax = left.xMax max right.xMax
        yMin = left.yMin min right.yMin
        yMax = left.yMax max right.yMax
        zMin = left.zMin min right.zMin
        zMax = left.zMax max right.zMax
      } else {
        for (p <- ps) {
          cMass += p.mass
          cPoint += p.x * p.mass
          if (p.x.x < xMin) xMin = p.x.x
          if (p.x.x > xMax) xMax = p.x.x
          if (p.x.y < yMin) yMin = p.x.y
          if (p.x.y > yMax) yMax = p.x.y
          if (p.x.z < zMin) zMin = p.x.z
          if (p.x.z > zMax) zMax = p.x.z
        }
        cPoint /= cMass
      }
      this
    }
  }
  private val root = buildTree(parts, 0, parts.length)

  private def buildTree(p: Array[Particle], start: Int, end: Int): Node = {
    if (end - start <= MAX_PARTS) {
      Node(p.slice(start, end), -1, 0, null, null).calcValues
    } else {
      //TODO figure out split dim (dim of largest spread)
      //TODO find Median in that dim
      //TODO recursively build children
    }
  }
  private def findMedian(p: Array[Particle], start: Int, end: Int, dim: Int): Unit = {
    val median = (start + end) / 2
    while (???) {
      val pivot = start //TODO pick better pivot
      //TODO swap piv to front if better
      var high = end - 1
      var low = start + 1
      while (high >= low) {
        if (p(low).x(dim) < p(start).x(dim)) { low += 1 }
        else {
          val tmp = p(low)
          p(low) = p(high)
          p(high) = tmp
          high -= 1
        }
      }
      val tmp = p(start)
      p(start) = p(high)
      p(high) = tmp
      //TODO check if high above median, adjust start and end
    }
  }

}



