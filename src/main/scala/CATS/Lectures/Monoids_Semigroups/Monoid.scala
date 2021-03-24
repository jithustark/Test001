package CATS.Lectures.Monoids_Semigroups

object Monoid extends App
{
  // Monoid and semigroup allow add or combine values

  // -> Operation that combines with Type(A, A) => A
  // -> an element empty of type A

  trait Monoid[A] {
    def combine(x: A, y: A): A
    def empty: A
  }


}
