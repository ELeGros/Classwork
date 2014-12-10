package sim

import scala.collection.mutable
class Simulation(val f: TimeStepForce, private val mp: mutable.Buffer[Particle], force: Expressions) extends Serializable {
  case class PotentialCollision(time: Double, p1: Int, p2: Int)
  def numParticles = mp.length
  def getParticles = mp
  def advance: Unit = {
    val tmp = System.nanoTime()
    val acc = f.calcAcceleration(this, force)
    val pq = new PriorityQueue((a:PotentialCollision, b:PotentialCollision) => if(a.time > b.time) 1 else -1)
    //    println(acc(0))
//    println("before " + (System.nanoTime() - tmp)*1e-9)
    (mp, acc).zipped.par.foreach(t => t._1.accelerate(t._2, f.dt))
    for(i <- 0 until numParticles; j <- i+1 until numParticles) {
      val time = Particle.collisionTime(mp(i), mp(j))
//      println(time)
      if(time>=0 && time < f.dt) {pq.enqueue(PotentialCollision(time, i, j))}
    }
    val times = Array.fill(numParticles)(0.0)
    while(!pq.isEmpty){
      val pc = pq.dequeue
      Particle.bounce(mp(pc.p1), mp(pc.p2), pc.time)
      times(pc.p1) = pc.time
      times(pc.p2) = pc.time
    }
    for(i <- 0 until numParticles)
      mp(i).step(f.dt - times(i))
//    println((System.nanoTime() - tmp)*1e-9)
  }
}