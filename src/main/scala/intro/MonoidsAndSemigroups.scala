package intro

/** A semigroup is a type class with a combine method that associatively
  * combines two elements
  */
trait MySemigroup[A] {
  def combine(x: A, y: A): A
}

/** A Monoid is an extension of a semigroup, that contains an empty element,
  * which when combined with any element will result in an idempotent operation,
  * returning the original element
  */
trait MyMonoid[A] extends MySemigroup[A] {
  def empty: A
}

/** Laws for defining monoids Combine operations must be associative Combining
  * with empty or identity should be idempotent and equal to original element
  */
object MonoidLaws {
  def associativeLaw[A](x: A, y: A, z: A)(implicit m: MyMonoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)(implicit m: MyMonoid[A]): Boolean = {
    (x == m.combine(x, m.empty)) && (x == m.combine(m.empty, x))
  }
}

object MyMonoids {
  trait Add[A] extends MyMonoid[A]
  implicit val addInt: Add[Int] = new Add[Int] {
    def combine(x: Int, y: Int): Int = x + y
    def empty: Int = 0
  }
}

// Monoids in Cats

import cats._
import cats.implicits._
import cats.syntax._

object CatsMonoidsAndSemigroups {
  // |+| is the operator for monoid combination in Cats
  val stringResult =
    "Hi" |+| " There" |+| Monoid[String].empty // empty is optional
  val intResult = 1 |+| 10

  /** SuperAdder exercise, using monoid type-safe combination with list reduce.
    * (List is also a monoid)
    */
  object SuperAdder {
    def reduceMonoid[A](items: List[A])(implicit m: Monoid[A]): A = {
      items.reduce((a, b) => m.combine(a, b))
    }

    def add[A: Monoid](items: List[A]): A = {
      reduceMonoid(items)
    }

    case class Order(totalCost: BigDecimal, quantity: Double)

    implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
      def combine(x: Order, y: Order): Order = {
        Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
      }
      def empty: Order = Order(0, 0)
    }
  }

  val listResult =
    List("a", "b") |+| List("c", "d") // type safe expression won't allow Ints
  // val listResult = List("a", "b") |+| List(1) // will throw compile error
  val listResult2 =
    List("a", "b") ++ List(
      1
    ) // regular combination will not throw compile error
}

object MonoidsAndSemigroups {
  import MyMonoids._
  import MonoidLaws._
  import CatsMonoidsAndSemigroups.SuperAdder
  def main(args: Array[String]): Unit = {
    val addIntIsAssociative = associativeLaw[Int](1, 2, 3)
    val addIntIsIdentity = identityLaw[Int](10)
    println(
      f"Monoid addInt is associative: $addIntIsAssociative; is identity: $addIntIsIdentity"
    )

    val addValues = List(1, 2, 3)
    val addResult = SuperAdder.add(addValues)
    val optAddValues = addValues.map(x => Option(x))
    val optAddResult = SuperAdder.add(optAddValues)
    import SuperAdder.Order
    val orderList  = List(Order(10.25, 20), Order(30.2, 2), Order(50.501, 10))
    val totalOrder = SuperAdder.add(orderList)
    println(totalOrder)
  }
}
