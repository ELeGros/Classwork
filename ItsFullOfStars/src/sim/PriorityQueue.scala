package sim

class PriorityQueue[A](comp: (A,A) => Int) {
  private class Node(var data: A, var prev: Node, var next: Node)
  var default: A = _
  private val end = new Node(default, null, null)
  end.next = end
  end.prev = end
  
  def enqueue(obj: A): Unit = {
    var rover = end.prev
    while(rover != end && comp(obj,rover.data)>0) rover = rover.prev
    rover.next.prev = new Node(obj, rover, rover.next)
    rover.next = rover.next.prev
  }
  
  def dequeue(): A = {
    val ret = end.next.data
    end.next = end.next.next
    end.next.prev = end
    ret
  }
  
  def peek(): A = end.next.data
  
  def isEmpty(): Boolean = end.next == end
}