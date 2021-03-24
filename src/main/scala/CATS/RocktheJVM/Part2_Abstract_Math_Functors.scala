package CATS.RocktheJVM

import scala.util.Try

object Part2_Abstract_Math_Functors extends App
{
  // Provides a map method

  val aModified = List(1,2,3).map(_ +1) // List(2,3,4)
  val aModifiedOption = Option(2).map(_ +1) // Some(3)
  val aModifiedTry = Try(43).map(_ +1) // Sucess(44)


  trait myfunctor[F[_]] {
    def map[A, B](initialValue: F[A])(f: A => B): F[B]
  }

  import cats.Functor  // Type class declaration
  import cats.instances.list._  // Type class instance

  val listFunctor = Functor[List]
  val increamentedNumbers = listFunctor.map(List(1,23,4,5,3))(_ +1)

  import cats.instances.option._
  val optionFunctor = Functor[Option]
  val optionNumbers = optionFunctor.map(Option(3))(_ +1)

  import cats.instances.try_._
  val tryFunctor = Functor[Try].map(Try(42))(_ +1)


// Generalizing API

  def do10xList(List: List[Int]): List[Int] = List.map(_ *10)
  def do10xOption(Option: Option[Int]): Option[Int] = Option.map(_ *10)
  def do10xTry(Try: Try[Int]): Try[Int] = Try.map(_ *10)


  // Generalize API
  def do10x[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] = functor.map(container)(_ *10)


  println(aModified ,aModifiedOption,aModifiedTry)
  println(increamentedNumbers ,optionNumbers,tryFunctor)
  println(do10xList(List(1,2,45,5)))
  println(do10xOption(Option(20)))
  println(do10xTry(Try(40)))

  println(do10x(List(1,2,45,5)))
  println(do10x(Option(20)))
  println(do10x(Try(40)))







}
