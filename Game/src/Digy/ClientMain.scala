package Digy

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import scala.swing.MainFrame

import scala.swing._; import event._
import javax.swing._

@remote trait RemoteClient {
  def updateWorld(world: World): Unit
}

object ClientMain extends UnicastRemoteObject with RemoteClient {
  val world = World(Vector[Entity]())
  val panel = new Render(world, 0, 640, 0, 640)
  def main(args: Array[String]): Unit = {
    val server = Naming.lookup("rmi://localhost/Digy_Server") match {
      case rs:RemoteServer => rs
      case _ => throw new RuntimeException("Server was not a server. Wait, what?")
      }
    val rPlayer = server.connect(this)
    val frame = new MainFrame {
      title = "Digy"
      contents = panel
      listenTo(panel.keys)
      reactions += {
         case KeyPressed(_, Key.Up,_,_) => rPlayer.upPressed()
         case KeyPressed(_, Key.Left,_,_) => rPlayer.leftPressed()
         case KeyPressed(_, Key.Right,_,_) => rPlayer.rightPressed()
      }
      panel.focusable = true
      panel.requestFocus
    }
    frame.open()
    while(true){
      
    }
      
  }
  def updateWorld(world: World): Unit = {
      panel.setWorld(world)
      panel.repaint
  }
}