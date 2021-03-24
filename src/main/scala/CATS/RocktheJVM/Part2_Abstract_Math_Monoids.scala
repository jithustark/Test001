package CATS.RocktheJVM



object Part2_Abstract_Math_Monoids extends App
{
  import cats.Semigroup
  import cats.instances.int._
  import cats.syntax.semigroup._ // |+| extension method

  val numbers = (1 to 1000).toList
  // |+| combine is always associative
  val sumleft = numbers.foldLeft(0)(_ |+| _)
  val sumRight = numbers.foldRight(0)(_ |+| _)


  // define a Genenral API
  // def combineFold[T](List: List[T])(implicit semigroup: Semigroup[T]): T = List.foldLeft()(_ |+| _)
  // Shows an error due to we are not providing the starting value for the foldLeft,
  // reason we are using general method that takes all values

  // Here comes Monoid which helps in getting the empty value.
  import cats.Monoid
  val intMonoid = Monoid[Int]
  val combineInt = intMonoid.combine(23, 999)
  val zero = intMonoid.empty // 0

  import cats.instances.string._
  val emptystring = Monoid[String].empty
  println(emptystring)
  val combineString = Monoid[String].combine("Jithu", " ADcmals")
  println(combineString)

  import cats.instances.option._
  val emptyOption = Monoid[Option[Int]].empty
  val combineOption = Monoid[Option[Int]].combine(Option(30), Option.empty[Int])
  val combineOption1 = Monoid[Option[Int]].combine(Option(30), Option(20))
  
  println(emptyOption)
  println(combineOption)
  println(combineOption1)


  // Extention methods for monoids
  val combines = Option(5) |+| Option(10)
println(combines)




  println(sumleft)
  println(sumRight)



  // TODO Function implement in combineFold
def combinedFold[T](List: List[T])(implicit monoid: Monoid[T]): T = List.foldLeft(monoid.empty)(_ |+| _)
 val  numbers1 = (1 to 100).toList
  println(combinedFold(numbers1))


  // TODO Combine a list of phone books as Maps [String, Int]
  val phonebooks = List(
    Map(
      "Alice" -> 235,
      "bob"-> 2345,

    ),
    Map(
      "ABS" -> 214324,
      "asdd" -> 2315767
    )
  )
  import cats.instances.map._
  println(combinedFold(phonebooks))

}
