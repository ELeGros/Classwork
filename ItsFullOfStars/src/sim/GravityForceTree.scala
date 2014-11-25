package sim

class GravityKDTree(initialParticles: Seq[Particle]) {
  private val parts = Array(initialParticles: _*)
  private val MAX_PARTS = 5
  private case class Node(ps: Seq[Particle], dim: Int, v: Double, left: Node, right: Node) {
    var cMass = 0.0
    var cPoint = Point3D(0, 0, 0)
    var xMin, xMax, yMin, yMax, zMin, zMax = 0.0
    var size = 0.0
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
      size = (xMax - xMin) max (yMax - yMin) max (zMax - zMin)
      this
    }
  }
  private val root = buildTree(parts, 0, parts.length)

  private def buildTree(p: Array[Particle], start: Int, end: Int): Node = {
    if (end - start <= MAX_PARTS) {
      Node(p.slice(start, end), -1, 0, null, null).calcValues
    } else {
      val dim = findDim(p, start, end)
      findMedian(p, start, end, dim)
      val mid = (end - start) / 2
      Node(Seq(), dim, p(mid).x(dim), buildTree(p, start, mid + 1), buildTree(p, mid + 1, end))
    }
  }

  private def findDim(p: Array[Particle], s: Int, e: Int): Int = {
    val xMin = p.view.map(_.x.x).min
    val xMax = p.view.map(_.x.x).max
    val yMin = p.view.map(_.x.y).min
    val yMax = p.view.map(_.x.y).max
    val zMin = p.view.map(_.x.z).min
    val zMax = p.view.map(_.x.z).max
    val seps = Array(xMax - xMin, yMax - yMin, zMax - zMin)
    seps.indexOf(seps.max)
  }

  private def findMedian(p: Array[Particle], s: Int, e: Int, dim: Int): Unit = {
    val median = (s + e) / 2
    var start = s
    var end = e
    var done = false
    while (!done) {
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
      if (high == median) {
        done = true
      } else if (high < median) {
        start = high + 1
      } else {
        end = high
      }
    }
  }
  def calcForce(p: Particle, theta: Double): Vect3D = {
    calcForceRecur(p, theta, root)
  }
  def calcForceRecur(p: Particle, theta: Double, n: Node): Vect3D = {
    if (n.ps.isEmpty) { //Internal Node
      val dist = p.x - n.cPoint
      if (n.size > theta * dist.magnitude) {
        calcForceRecur(p, theta, n.left) + calcForceRecur(p, theta, n.right)
      } else {
        val mag = (p.x - n.cPoint).magnitude max .05
        (p.x - n.cPoint) * ((-1) * n.cMass / (mag * mag * mag))
      }
    } else { //Leaf Node
      var a = Vect3D(0, 0, 0)
      for (p2 <- n.ps) {
        val mag = (p.x - p2.x).magnitude max .05
        if (mag > 0)
          a = a + (p.x - p2.x) * ((-1) * p2.mass / (mag * mag * mag))
      }
      a
    }
  }

}



