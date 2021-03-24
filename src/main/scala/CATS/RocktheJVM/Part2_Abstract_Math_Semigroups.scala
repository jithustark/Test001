package CATS.RocktheJVM

object Part2_Abstract_Math_Semigroups extends App
{

  // Semigroups combine elements of the same datatype
  import cats.Semigroup
  import cats.instances.int._

  val naturalIntSemigroup = Semigroup[Int]
  val intcombination = naturalIntSemigroup.combine(2, 46) // Addtion

  import cats.instances.string._
  val naturalStringSemigroup = Semigroup[String]
  val stringcombination = naturalStringSemigroup.combine("jithu", " stark") // Concatenate

  // Specific API
  def reduceInts(List: List[Int]): Int = List.reduce(naturalIntSemigroup.combine)
  def reduceStrings(List: List[String]): String = List.reduce(naturalStringSemigroup.combine)

  // General API Can be used for any datatypes
def reduceThings[T](list: List[T]) (implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)


  // Support  a New Type
  case class Expense(id: Long, amount: Double)
  implicit val expenseSemigroup: Semigroup[Expense] = Semigroup.instance[Expense] {
    (e1, e2) => Expense(Math.max(e1.id,e2.id), e1.amount + e2.amount )
  }

//Convience Extension method
  // Extension methods from semigroup - |+| [Combine]
  import cats.syntax.semigroup._
  val anIntSum = 2 |+| 3  // requires the presence of an implicit Semigroup[Int]

  val aStrConcat = "jithu " |+| " Wayne"
  val aCombineExpense = Expense(4, 48) |+| Expense(50, 40)



  def reduceThings2[T](List: List[T])(implicit semigroup: Semigroup[T]): T =
    List.reduce(_ |+| _)





  println(intcombination)
  println(stringcombination)

  val numbers = (1 to 10).toList
  println(reduceInts(numbers))

  val str = List("jithu", " Tony"," Captain ")
  println(reduceStrings(str))

println(reduceThings(str))
  println(reduceThings(numbers))

  import cats.instances.option._
  val numberOptions: List[Option[Int]] = numbers.map(n => Option(n))
  println(reduceThings(numberOptions))


  //test expenses-1
  val expenses = List(Expense(1, 99 ), Expense(2, 35), Expense(43, 10))
  println(reduceThings(expenses))



  println(anIntSum)
  println(aStrConcat)
  println(aCombineExpense)


  println(reduceThings2(expenses))


}
