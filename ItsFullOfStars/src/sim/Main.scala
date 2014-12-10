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
    val parts = for(i <- 1 to 1000) yield{ 
      val r = 0.02+math.random*math.random*0.3
      new Particle(Point3D(util.Random.nextGaussian()*10,util.Random.nextGaussian()*10,util.Random.nextGaussian()*10*0), Vect3D(0,0,0), r*r*r, r)
    }
//    val parts = Seq[Particle](new Particle(Point3D(1,0,0), Vect3D(0,0,0), 10.0, 0.5), new Particle(Point3D(-1,0,0), Vect3D(0,0,0), 10.0, 0.5), new Particle(Point3D(0,1,0), Vect3D(0,0,0), 10.0, .5))
    println("Please enter What force you would like to use, providing a distance x")
    val s = readLine
    val e = new Expression(s)
    val sim = new Simulation(new GravityTreeForce(0.001), parts.toBuffer, e)
    val simP = new SimPlot(sim, -10, 10, -10, 10)
    val frame = new MainFrame { 
      title = "Sim"
      contents = simP
    }
    frame.open
    while(true)
    {
//    val start = System.nanoTime()
//      Thread.sleep(10)
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