package Digy

import java.rmi.server.UnicastRemoteObject

class RemotePlayerImpl(playerID: Int) extends UnicastRemoteObject with RemotePlayer {
  def upPressed(): Unit = {
    Main.sendUp(playerID) 
  }
  def downPressed(): Unit = {
    Main.sendDown(playerID)
  }
  def leftPressed(): Unit = {
    Main.sendLeft(playerID)
  }
  def rightPressed(): Unit = { 
    Main.sendRight(playerID)
  }
}

object RemotePlayerImpl {
  def apply(pID: Int): RemotePlayerImpl = new RemotePlayerImpl(pID)
}