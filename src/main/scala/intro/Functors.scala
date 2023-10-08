package intro

import cats._
import cats.implicits._
import cats.instances.function._
import cats.syntax.functor._

/**
  * a Functor is a class that can encapsulate computations, used to transform
  * the type of the underlying data
  */
trait MyFunctor[A] {
  def map[B](f: A => B): MyFunctor[B]
}

object CatsFunctors {
  // using Function1 as a functor
  val func1: Int => Double = (x: Int) => x.toDouble
  val func2: Double => Double = x => x * 2

  // val res1 = (func1 map func2)(1)
  val res2 = (func1 andThen func2)(1)

}

object Functors extends App {
  import CatsFunctors._
  println(res2)
}
