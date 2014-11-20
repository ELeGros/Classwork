package sim

import java.io.File

import java.io.FileOutputStream
import java.io.BufferedOutputStream
import java.io.ObjectOutputStream
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.FileInputStream
import collection.mutable
import swing._

object Main {

  def main(args: Array[String]): Unit = {
    val parts = for(i <- 1 to 3000) yield(new Particle(Point3D(util.Random.nextGaussian()*10,util.Random.nextGaussian()*10,util.Random.nextGaussian()*10), Vect3D(0,0,0),util.Random.nextDouble(), util.Random.nextDouble()))
    val sim = new Simulation(new GravityForce(0.01), parts.toBuffer)
    val simP = new SimPlot(sim, -10, 10, -10, 10)
    val frame = new MainFrame {
      title = "Sim"
      contents = simP
    }
    frame.open
    while(true)
    {
//    val start = System.nanoTime()
      sim.advance
//    println((System.nanoTime() - start)*1e-9)
      simP.repaint()
    }
    saveSimulation(sim, new java.io.File("Test.bin"))
  }

  def saveSimulation(sim: Simulation, file: File): Unit = {
    val oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))
    oos.writeObject(sim)
    oos.close()
  }

  def loadSimulation(file: File): Simulation = {
    val ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))
    val sim = ois.readObject() match {
      case s: Simulation => s
      case _ => throw new IllegalArgumentException("File did not include a simulation")
    }
    ois.close()
    sim
  }

}