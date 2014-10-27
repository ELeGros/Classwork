package Digy

import java.rmi.server.UnicastRemoteObject

@remote trait RemoteClient {
  def updateWorld(world: World): Unit
}

object ClientMain extends UnicastRemoteObject with RemoteClient {
  def main(args: Array[String]): Unit = {
    //TODO render/panel/frame
    
  }
  def updateWorld(world: World): Unit = {
    ???
  }
}