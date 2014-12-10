package sim

class GravityKDTree(initialParticles: Seq[Particle]) {
  private val parts = Array(initialParticles: _*)
  private val MAX_PARTS = 8
  private case class Node(ps: Seq[Particle], dim: Int, v: Double, left: Node, right: Node) {
    var cMass = 0.0
    var cPoint = Point3D(0, 0, 0)
    var xMin, xMax, yMin, yMax, zMin, zMax = 0.0
    var size = 0.0
    def calcValues: Node = {
      //      val tmp = System.nanoTime()
      if (ps.isEmpty) {
        cMass = left.cMass + right.cMass
        //        println("mass = "+ cMass)
        cPoint = (left.cPoint * left.cMass + right.cPoint * right.cMass) / cMass
        xMin = left.xMin min right.xMin
        xMax = left.xMax max right.xMax
        yMin = left.yMin min right.yMin
        yMax = left.yMax max right.yMax
        zMin = left.zMin min right.zMin
        zMax = left.zMax max right.zMax
      } else {
        for (p <- ps) {
          //          println(p.mass)
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
      //      println((System.nanoTime() - tmp)*1e-9)
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
      val mid = (end + start) / 2
      Node(Seq(), dim, p(mid).x(dim), buildTree(p, start, mid + 1), buildTree(p, mid + 1, end)).calcValues
    }
  }

  private def findDim(p: Array[Particle], s: Int, e: Int): Int = {
    val xMin = p.view.slice(s, e).map(_.x.x).min
    val xMax = p.view.slice(s, e).map(_.x.x).max
    val yMin = p.view.slice(s, e).map(_.x.y).min
    val yMax = p.view.slice(s, e).map(_.x.y).max
    val zMin = p.view.slice(s, e).map(_.x.z).min
    val zMax = p.view.slice(s, e).map(_.x.z).max
    val seps = Array(xMax - xMin, yMax - yMin, zMax - zMin)
    seps.indexOf(seps.max)
  }

  private def findMedian(p: Array[Particle], s: Int, e: Int, dim: Int): Unit = {

    val median = (s + e) / 2
    var start = s
    var end = e
    //    val print = e-s == p.length
    var done = false
    //    println(p.size)
    while (!done) {
      //      if(print) println("Start: " + start + ", End: " + end)
      val pivot = start //TODO pick better pivot
      //TODO swap piv to front if better
      var high = end - 1
      var low = start + 1
      while (high >= low) {
        if (p(low).x(dim) <= p(start).x(dim)) { low += 1 }
        else {
          val tmp = p(low)
          p(low) = p(high)
          p(high) = tmp
          high -= 1
        }
      }
      val tmp2 = p(start)
      p(start) = p(high)
      p(high) = tmp2
      if (high == median) {
        done = true
      } else if (high < median) {
        start = high + 1
      } else {
        end = high
      }

    }
    //        println("Find Median Time: "+(System.nanoTime()-tmp)*1e-9)
  }
  def calcForce(p: Particle, theta: Double, e: Expressions): Vect3D = {
    calcForceRecur(p, theta, root, e)
  }
  def calcForceRecur(p: Particle, theta: Double, n: Node, e: Expressions): Vect3D = {
    if (n.ps.isEmpty) { //Internal Node
      val dist = p.x - n.cPoint
      if (n.size > theta * dist.magnitude) {
        calcForceRecur(p, theta, n.left, e) + calcForceRecur(p, theta, n.right, e)
      } else {
        val mag = (p.x - n.cPoint).magnitude max .05
        //        println(mag)
        val r = (p.x - n.cPoint)*e(mag)/(mag)*n.cMass// * ((-1) * n.cMass / (mag * mag * mag))
        //        println(n.cMass)
        r
      }
    } else { //Leaf Node
      var a = Vect3D(0, 0, 0)
      for (p2 <- n.ps) {
        val mag = (p.x - p2.x).magnitude max .05
        if (mag > 0)
          a = a + (p.x - p2.x) * e(mag)/(mag)*p2.mass//*((-1) * p2.mass / (mag * mag * mag))
      }
      a
    }
  }

}



