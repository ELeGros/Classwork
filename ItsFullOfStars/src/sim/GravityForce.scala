package sim

class GravityForce(val dt: Double) extends TimeStepForce {
  def calcAcceleration(s: Simulation): IndexedSeq[Vect3D] = {
    val parts = s.getParticles
    (for (p1 <- parts.toIndexedSeq.par) yield {
      var a = Vect3D(0, 0, 0)
      for (p2 <- parts) {
        val mag = (p1.x - p2.x).magnitude max .5
        if(mag > 0)
          a = a + (p1.x - p2.x) * ((-1) * p2.mass / (mag * mag * mag))
//        a = a + (p1.x - p2.x) * ((math.cos(mag))/((mag)*p2.mass))
      }
      a
    }).seq.toIndexedSeq
  }
}

class GravityTreeForce(val dt: Double) extends TimeStepForce {
  def calcAcceleration(s: Simulation): IndexedSeq[Vect3D] = {
		val parts = s.getParticles
		val tmp = System.nanoTime()
    val tree = new GravityKDTree(parts)
		println("build " + (System.nanoTime() - tmp) * 1e-9)
    (for (p1 <- parts.toIndexedSeq.par) yield {
      tree.calcForce(p1, 0.5)
    }).seq.toIndexedSeq
  }
}