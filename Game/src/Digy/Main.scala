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
//    java.rmi.registry.LocateRegistry.createRegistry(7078)
//    Naming.bind("Digy_Server", this)
    var world = World(Vector[Entity](Entity(new Point2D(32, 55), (1,1), Vect2D(0,0), false)))
    var panel = new Render(world, 0, 600, 0, 600)
    val frame = new MainFrame {
      title = "Digy"
      contents = panel
    }
    frame.open()
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