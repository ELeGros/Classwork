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