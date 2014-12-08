package Mud

import java.io.BufferedReader
import java.io.PrintStream
import java.io.OutputStream
import java.io.InputStream
import java.io.InputStreamReader
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable
import collection.mutable

class Player(private var inventory: List[Item], private var currentRoom: Room, private val input: InputStream, private val output: PrintStream) {

  private var name = ""
  output.println("What is your name?")
  def getFromInventory(itemName: String): Option[Item] = {
    var count = 0
    for (item <- inventory) {
      //      println(item.name)
      if (itemName.equals(item.name)) {
        inventory = inventory.patch(count, List(), 1)
        //        println("here")
        return Some(item)
      }
      count += 1
    }
    println("That item is not in your inventory")
    None
  }

  val commands: Map[String, (Player, String, Vector[Room], mutable.Buffer[Player]) => Unit] = Map(
    "look" -> lookHelper,
    "drop" -> dropHelper,
    "get" -> getHelper,
    "inv" -> invHelper,
    "North" -> directionHelper,
    "South" -> directionHelper,
    "East" -> directionHelper,
    "West" -> directionHelper,
    "Say" -> sayHelper,
    "Tell" -> tellHelper
    )

  def pollInput(rooms: Vector[Room], players: mutable.Buffer[Player]): Unit = {
    if (input.available() != 0) {
      val buf = new Array[Byte](input.available())
      input.read(buf)
      val line = new String(buf).trim
      if (name.isEmpty()) {
        name = line
        getRoom.printDescription(output)
      } else commands(line.split(" ")(0))(this, line, rooms, players)
      //TODO do stuff with line
    }
  }

  def addToInventory(item: Item): Unit = {
    inventory = item :: inventory
    None
  }

  def getRoom(): Room = {
    currentRoom
  }

  def setRoom(room: Room): Unit = {
    currentRoom = room
  }

  def printInventory(): Unit = {
    output.println("INVENTORY:")
    for (obj <- inventory) output.println(obj.name)
  }
  
  def sayHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    for(c <- ps.filter(_.getRoom == p.getRoom)) c.output.println(args.substring(4))
  }

  def tellHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    val c = args.split(" ")(1)
    if(ps.indexOf(c) != - 1)
    {
      ps(ps.indexOf(c)).output.println(p.name + " Said: " + args.substring(4 + c.size))
    }
    else
      output.println("That player doesn't exist!")
  }
  def invHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    p.printInventory()
  }

  def getHelper(p: Player, args: String, r: Vector[Room],ps : mutable.Buffer[Player]): Unit = {
    val item = p.getRoom().getItem(args.split(" ")(1))
    item match {
      case Some(i) => p.addToInventory(i)
      case None => output.println("that item is not here")
    }
  }

  def dropHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    val item = p.getFromInventory(args.split(" ")(1))
    println(item)
    item match {
      case Some(i) => p.getRoom.addItem(i)
      case None => output.println("you do not have that item")
    }
  }

  def lookHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    p.getRoom.printDescription(output)
  }

  def directionHelper(p: Player, args: String, r: Vector[Room], ps: mutable.Buffer[Player]): Unit = {
    val e = p.getRoom().exits
    println("DEBUG: Input == " + args)
    var found = false
    breakable {
      for (ex <- e) {
        //        println("DEBUG: The current exit's direction is " + ex.direction)
        if (ex.direction.equals(args.split(" ")(0).trim())) {
          p.setRoom(r(r.map(_.name).indexOf(ex.destination)))
          p.getRoom.printDescription(output)
          found = true
          break
        }
      }
      if (found == false) {
        output.println("There is no exit in that direction.")
      }
    }
  }
}

object Player {
  def apply(inv: List[Item], currRoom: Room, is: InputStream, os: OutputStream): Player = {
    new Player(inv, currRoom, is, new PrintStream(os))
  }
}