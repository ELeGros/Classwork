package basics

class UnsortLinkedList[A](comp: (A, A) => Int) {

  var head: node = null
  case class node(var next: node, label: A)

  def enqueue(label: A): Unit = {
    head = node(this.head, label)
  }

  def dequeue(): A = {
    require(head != null, "You can't dequeue an empty queue")
    var n1 = head
    var n2:node = null
    while (n2 != null) {
      if (comp(n1.label, n2.next.label) < 0) {
        n1 = n2
      }
      n2 = n2.next
    }
    val out = n1.next
    n1.next = out.next
    out.label
  }

  def peak(): A = {
    require(head != null, "You can't peak an empty queue")
    var n1 = head
    var n2 = head
    while (n2 != null) {
      if (comp(n1.label, n2.next.label) < 0) {
        n1 = n2
      }
      n2 = n2.next
    }
    n2.next.label

  }

  def isEmpty(): Boolean = {
    head == null
  }
}

