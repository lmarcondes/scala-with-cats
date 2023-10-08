package intro

import cats._
import cats.implicits._

object WorkingWithCats {
  import CatsConverters._
  def main(args: Array[String]): Unit = {
    val cat = Cat("Jamiroquai", 20, "purple")
    val cat2 = Cat("Jamiroquai", 30, "brown")
    val cat3 = Cat("Bob", 32, "brown")
    println(cat.show)
    println(cat === cat2)
    println(cat === cat3)
    println(cat2 === cat3)
  }
}

object CatsConverters {
  val showInt: Show[Int] = Show.apply[Int]

  implicit val catShow: Show[Cat] = Show.show { cat =>
    f"${cat.name} is a ${cat.age} year-old ${cat.color} cat."
  }

  implicit val catsEq: Eq[Cat] = new Eq[Cat] {
    def eqv(x: Cat, y: Cat): Boolean = {
      println(f"Comparing cats ${x.name} and ${y.name}")
      x.name == y.name
    }
  }
}

object CatsEq {
  val otherCat = Cat("dilo", 10, "laranja")
}
