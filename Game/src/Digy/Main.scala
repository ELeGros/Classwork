package Digy

import java.rmi.server.UnicastRemoteObject
import collection.mutable
import java.rmi.Naming
import java.util.Timer
import scala.swing.Swing
import scala.swing.MainFrame

@remote trait RemoteServer {
  def connect(client: RemoteClient):RemotePlayer 
}

object Main extends UnicastRemoteObject with RemoteServer {
  val players = mutable.Buffer[RemotePlayer]()
  def main(args: Array[String]): Unit = {
    java.rmi.registry.LocateRegistry.createRegistry(1099)
    Naming.bind("Digy Server", this)
    var world = World(Vector[Entity]())
    var panel = new Render(world, 0, 600, 0, 600)
    val frame = new MainFrame {
      title = "Digy"
      contents = panel
    }
    while(true)
    {
      Thread.sleep(100)
      world = world.update
      panel.repaint 
    }
    
  }

  
  def connect(client: RemoteClient):RemotePlayer = {
    ???
  }
}