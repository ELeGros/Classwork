package Mud

class Item(val name:String) {

}

object Item {
  def apply(n: xml.Node): Item = {
    val name = (n \ "@name").mkString
    new Item(name)
  }
  
  def apply(n: String): Item = {
    new Item(n)
  }
}