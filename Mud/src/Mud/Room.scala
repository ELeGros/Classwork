package Mud

import java.io.PrintStream

class Room(val name: String, val description: String, private var items: List[Item], val exits: List[Exit]) {
  def printDescription(out: PrintStream): Unit = {
    out.println(description)
    out.println("ITEMS IN ROOM:")
    items.foreach(n => out.println(n.name))
  }

  def getExit(dir: String): Option[Exit] = {
    for (exit <- exits) {
      if (exit.direction.equals(dir)) {
        Some(exit)
      }
    }
    None
  }

  def addItem(item: Item): Unit = {
    items = item :: items
  }
  def getItem(itemName: String): Option[Item] = {
    var count = 0
    for (item <- items) {
      if (item.name.equals(itemName)) {
        items = items.patch(count, List(), 1)
        return Some(item)
      }
      count += 1
    }
    None
  }

  def dropItem(item: Item): Unit = {
    items = item :: items
  }
}

object Room {
  def apply(n: xml.Node): Room = {
    val name = (n \ "@name").mkString
    val i = (n \ "item").toList
    var items = for (item <- i) yield Item(item)
    val e = (n \ "exit").toList
    val exits = for (exit <- e) yield Exit(exit)
    val description = (n \ "description")(0).text
    new Room(name, description, items, exits)
  }
}
