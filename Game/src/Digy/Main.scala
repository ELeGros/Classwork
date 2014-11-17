package Digy

import java.rmi.server.UnicastRemoteObject
import collection.mutable
import java.rmi.Naming
import java.util.Timer
import scala.swing.Swing
import scala.swing.MainFrame

@remote trait RemoteServer {
  def connect(client: RemoteClient): RemotePlayer
}

object Main extends UnicastRemoteObject with RemoteServer {
  val players = mutable.Buffer[RemotePlayer]()
  private var world = World(Vector[Entity]())
  def main(args: Array[String]): Unit = {
    java.rmi.registry.LocateRegistry.createRegistry(7078)
    Naming.bind("Digy_Server", this)
    var pID = 0
    var panel = new Render(world, 0, 640, 0, 640)
    val frame = new MainFrame {
      title = "Digy"
      contents = panel
    }
    frame.open()
    while (true) {
      Thread.sleep(100)
      world = world.update
      panel.setWorld(world)
      panel.repaint
    }

  }

  def sendUp(playerID: Int): Unit = {
    world = world.sendUp(playerID)
    //TODO race condition :P
  }

  def sendDown(playerID: Int): Unit = { 
    world = world.sendDown(playerID)
    //TODO race condition :P
  }

  def sendLeft(playerID: Int): Unit = {
    world = world.sendLeft(playerID)
    //TODO race condition :P
  }

  def sendRight(playerID: Int): Unit = {
    world = world.sendRight(playerID)
    //TODO race condition :P
  }

  def connect(client: RemoteClient): RemotePlayer = {
    null
  }
}