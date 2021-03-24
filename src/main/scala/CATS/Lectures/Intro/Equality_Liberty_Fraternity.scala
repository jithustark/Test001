package CATS.Lectures.Intro

import cats.Eq
import cats.implicits.catsSyntaxEq
import cats.syntax._
import cats.instances.int._

object Equality_Liberty_Fraternity extends App
{
  val eqInt = Eq[Int]
  println(eqInt.eqv(123, 123))
  println(eqInt.eqv(123, 1234))

  // We cant complie different types by using eqv as we do in scala it will throw an error

//  println(eqInt.eqv(123, "123"))

  // We can also use  === , =!= use import cats.implicits.catsSyntaxEq
print(123 === 123)

  print(123 =!= 123)

}
