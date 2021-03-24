package CATS.Lectures.Intro

import cats.Show

import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._
import java.util.Date

object Intro_1 extends App {

  // For Printing Data

  val ShowInt: Show[Int] = Show.apply[Int]
  val ShowString: Show[String] = Show.apply[String]


  val IntAsString: String = ShowInt.show(123)
  val StringAsString: String = ShowString.show("abs")

  println(IntAsString)
  println(StringAsString)

  // We can make this much simpler using, cats.syntax.show

  val ShowInt1 = 123.show
  val ShowString1 = "abc".show

  println(ShowInt1)
  println(ShowString1)

  //

  implicit val DateShow: Show[Date] =
    Show.show(date => s"{date.getTime}ms since of epoch")


}
