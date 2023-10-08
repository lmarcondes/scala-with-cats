trait AList[A] {
  def map[B](f: A => B): AList[B]
}

case class MyList[A](elems:A*) extends AList[A] {
  def map[B](f: A => B): MyList[B] = {
    val values = for (element <- elems) yield f(element)
    MyList(values:_*)
  }
}

object App {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
  }
}
