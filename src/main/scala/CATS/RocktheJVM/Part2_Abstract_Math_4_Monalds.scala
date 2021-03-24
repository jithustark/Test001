package CATS.RocktheJVM

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

object Part2_Abstract_Math_4_Monalds extends App
{

  // Lists
  val numbersList = List(1,2,3)
  val charsList = List('a', 'b', 'c')

  //TODO 1.1 create all the combinations for numbers and charsList
  val combinations = numbersList.flatMap(n => charsList.map(c => (c, n)))
  val combinations1 = for {
    n <- numbersList
    c <- charsList
  } yield( n,c )

  val numberOption = Option(5)
  val charOption = Option("a")


  val combinations2 = for {
    n <- numberOption
    c <- charOption
  } yield( n,c )




  // Future
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val numberFuture = Future(42)
  val charFuture = Future("z")

  val combinations3 = for {
    n <- numberFuture
    c <- charFuture
  } yield( n,c )


  /*
  Pattern
  - wrapping a value into a M value
  - the flatMap mechanism

  Monads

   */







  println(combinations)
  println(combinations1)
  println(combinations2)
  println(combinations3)




}
