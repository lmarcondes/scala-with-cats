package intro

object PrintableInstances {
  trait Printable[A] {
    def format(elem: A): String
  }

  implicit val stringPrint: Printable[String] = new Printable[String] {
    def format(elem: String): String = elem
  }

  implicit val intPrinter: Printable[Int] = new Printable[Int] {
    def format(elem: Int): String = elem.toString
  }

  implicit val catPrinter: Printable[Cat] = new Printable[Cat] {
    def format(elem: Cat): String = {
      f"${elem.name} is a ${elem.age} year-old ${elem.color} cat."
    }
  }
}

object PrintableSyntax {
  import PrintableInstances._
  implicit class PrintableOps[A](elem: A) {
    def print(implicit conv: Printable[A]): String = {
      conv.format(elem)
    }
  }
  implicit def optionPrint[A](implicit
      printer: Printable[A]
  ): Printable[Option[A]] = {
    new Printable[Option[A]] {
      def format(elem: Option[A]): String = elem match {
        case None        => ""
        case Some(value) => printer.format(value)
      }
    }
  }
}

object PrintableObj {
  import PrintableInstances._

  def print[A](elem: A)(implicit printable: Printable[A]): String = {
    printable.format(elem)
  }

}

final case class Cat(name: String, age: Int, color: String)

object TypeClasses {
  def main(args: Array[String]): Unit = {
    import PrintableInstances._
    import PrintableSyntax._
    println(
      PrintableObj.print("Hey")
    )

    val cat = Cat("Jamiroquai", 20, "purple")
    println(
      PrintableObj.print(cat)
    )

    println(
      cat.print
    )

    val optCat = Option(Cat("lucky", 10, "brown"))
    println(
      optCat.print
    )
  }
}
