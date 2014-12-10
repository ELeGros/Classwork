package sim

trait TimeStepForce extends Serializable{
  val dt: Double
  def calcAcceleration(s: Simulation, e: Expressions): IndexedSeq[Vect3D]
}