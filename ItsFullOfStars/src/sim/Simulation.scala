package sim

import scala.collection.mutable
class Simulation(val f: TimeStepForce, private val mp: mutable.Buffer[Particle]) extends Serializable {
  def numParticles = mp.length
  def getParticles = mp
  def advance: Unit = {
    val tmp = System.nanoTime()
    val acc = f.calcAcceleration(this)
    //    println(acc(0))
    println("before " + (System.nanoTime() - tmp)*1e-9)
    (mp, acc).zipped.par.foreach(t => t._1.advance(t._2, f.dt))
    println((System.nanoTime() - tmp)*1e-9)
  }
}