package Digy

import java.rmi.server.UnicastRemoteObject
import collection.mutable
import java.rmi.Naming
import java.util.Timer
import scala.swing.Swing

@remote trait RemoteServer {
  def connect(client: RemoteClient):RemotePlayer 
}

object Main extends UnicastRemoteObject with RemoteServer {
  val players = mutable.Buffer[RemotePlayer]()
  def main(args: Array[String]): Unit = {
    java.rmi.registry.LocateRegistry.createRegistry(1099)
    Naming.bind("Digy Server", this)
    var world = World()
    val timer = new Timer(100, Swing.ActionListener(e=> {
      world = world.update
      panel.repaint
    }))
  }

  
  def connect(client: RemoteClient):RemotePlayer = {
    ???
  }
}