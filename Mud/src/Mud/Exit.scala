package Mud

class Exit(val destination: String, val direction: String) {

}

object Exit {
  def apply(n: xml.Node): Exit = {
    val direction = (n \ "@direction").mkString
    val destination = (n \ "@destination").mkString
    new Exit(destination, direction)
  }
}