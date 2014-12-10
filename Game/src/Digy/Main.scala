package Digy

import java.rmi.server.UnicastRemoteObject
import collection.mutable
import java.rmi.Naming
import java.util.Timer
import scala.swing.Swing
import scala.swing.MainFrame
import java.util.Stack

@remote trait RemoteServer {
  def connect(client: RemoteClient): RemotePlayer
}

object Main extends UnicastRemoteObject with RemoteServer {
  val ws = new Stack[World]()
  val players = mutable.Buffer[(RemotePlayer, RemoteClient)]()
  private var world = World(Vector[Entity]())
  def main(args: Array[String]): Unit = {
    java.rmi.registry.LocateRegistry.createRegistry(1099)
    Naming.bind("Digy_Server", this)
    var pID = 0
    while (true) {
      Thread.sleep(100)
      world = world.update
      ws.push(world)
      for ((p, c) <- players) c.updateWorld(world)
    }

  }

  def sendUp(playerID: Int): Unit = {
    //    println(players.size)
    //    println(world.getEntities.size)
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

  def sendBack(playerID: Int): Unit = {
    for (i <- 1 to 10) if (!ws.empty()) world = ws.pop()
  }

  def connect(client: RemoteClient): RemotePlayer = {
    val p = Player(Point2D(34, 55), (3f, 3f), Vect2D(0, 0), false)
    world = world.addEntity(p)
    println(world.getEntities)
    val remoteP = new RemotePlayerImpl(players.size)
    players += (remoteP -> client)

    remoteP
  }
}