package basics

object Sorts extends App {
  def insertSort[A <% Ordered[A]](arr: Array[A]):Unit = {
    for(i <- 1 until arr.length) {
      var j = i
      val tmp = arr(i)
      while(j>0 && tmp<arr(j-1)) {
        arr(j) = arr(j-1)
        j -= 1
      }
      arr(j) = tmp
    }
  }
  
    def insertSortTwo[A](arr: Array[A])( lt: (A,A) => Boolean):Unit = {
    for(i <- 1 until arr.length) {
      var j = i
      val tmp = arr(i)
      while(j>0 && lt(tmp,arr(j-1))) {
        arr(j) = arr(j-1)
        j -= 1
      }
      arr(j) = tmp
    }
  }
  
  val nums = Array(true, false, true)
  insertSortTwo(nums)(_<_)
  nums.foreach(println)
}