package sim

trait TimeStepForce extends Serializable{
  val dt: Double
  def calcAcceleration(s: Simulation): IndexedSeq[Vect3D]
}