package Mud

import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable
import scala.xml.XML
import java.net.ServerSocket
import collection.mutable
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object main {
  val connectionQue = new LinkedBlockingQueue[Socket]()
  def main(args: Array[String]): Unit = {
    val x = XML.loadFile("map.xml")
    val r = (x \ "room")
    val rooms = { for (room <- r) yield Room(room) }
    val players = mutable.Buffer[Player]()
    Future{connectionHandler}
    while (true) {
      while(!connectionQue.isEmpty()){
        val connection = connectionQue.take()
        players+= Player(List[Item](), rooms(0), connection.getInputStream(), connection.getOutputStream())
      }
      var copy = players
      players.foreach(_.pollInput(rooms.toVector, copy))
//      commands(input.split(" ")(0))(player, input, rooms.toVector)

    }
  }
  
  def connectionHandler() {
    val s = new ServerSocket(8040)
    while(true) {
      val connect = s.accept()
      connectionQue.put(connect)
    }
  }
}
