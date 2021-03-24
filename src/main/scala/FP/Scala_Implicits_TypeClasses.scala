package FP


import java.util.Date

object Scala_Implicits_TypeClasses extends App
{
// implicits helps us to tell the complier to do some work to us

  def multiply(num1: Int)(num2: Int): Unit = {
    printf(s"$num1 x $num2 is $num1 * $num2")
  }

  multiply(2)(3)

//implcits helps us to define like a default value and by not passing those values also we can run the function.
  // The compiler helps us in doing this, and compiler should have only type of scope (one val)

  def multiply1(num1: Int)(implicit num2: Int): Unit = {
    printf(s"\n $num1 x $num2 is $num1 * $num2")
  }

  implicit val randomNumber: Int = 4

  multiply1(2)


  // In OOPS traits helps to remove the polymorphism

  trait human {
    def sayhello(name: String): Unit = {
      print(s"\n Hello $name")
    }
  }

  class Person extends human{
    def Hello(name: String) = {
      sayhello(name)
    }
  }

  val person = new Person
  person.Hello("Chris")

// here add method can take any datatype of input as we are not providing any specific type of them
  def add[A] (x: A): Unit = {
    print(s"\n Hello $x")
  }




add("Chris10")
  add(10)



  //
//  // Without Type Classes
//  final case class Person1(name: String, age: Int)
//  final case class Dog(name: String)
//
//  object PersonChat {
//    // Creating a chat method for the Person class [Without Type Classes ]
//    def chat(person: Person1): Unit = {
//      print(s" \n Hello my name is ${person.name}")
//    }
//
//  }
//
//  object DogChat {
//    // Creating a chat method for the Person class [Without Type Classes ]
//    def chat(dog: Dog): Unit = {
//      print(s" \n Hello my name is ${dog.name}")
//    }
//
//  }


//  // With Type Classes
//  final case class Person1(name: String, age: Int)
//  final case class Dog(name: String)
//
//  trait canChat[A]{
//    def chat(something: A): String
//  }
//
//
//  object PersonChat extends canChat[Person1]{
//    // Creating a chat method for the Person class [Without Type Classes ]
//    def chat(person: Person1): Unit = {
//      print(s" \n Hello my name is ${person.name}")
//    }
//
//  }
//
//  object DogChat extends canChat[Dog]{
//    // Creating a chat method for the Person class [Without Type Classes ]
//    def chat(dog: Dog): Unit = {
//      print(s" \n Hello my name is ${dog.name}")
//    }
//
//  }
//

// Without Type classes
//
//  trait Htmlwriter {
//    def ConvertToHtml: Unit
//  }
//
//
//  final case class Person1(name: String, age: Int) extends Htmlwriter{
//    def ConvertToHtml: Unit = println(s"\n <h1> The name is $name and age is $age")
//  }
//
//val person1 = Person1("Ben", 8)
//  println(person1.ConvertToHtml)
//
//}


// - The actual Type class
// - Type Class Instance
// - Interface Using implicit parameter
// - Interface Using enrichment and implicits

  // 1 - The actual Type class
  trait Htmlwriter[A] { // with any type parameter
    def ConvertToHtml(something: A): Unit
  }

  final case class Person1(name: String, age: Int)

  // 2 - Type Class Instance
  object PersonTypeClassInstance{
    implicit val PersonInstance = new Htmlwriter[Person1] {
      def ConvertToHtml(something: Person1): Unit  ={
        println(s"\n <h1> The name is $something.name and age is $something.age")
      }
    }
  }

  import PersonTypeClassInstance._

  object DateTypeClassInstance{
    implicit val DateInstance = new Htmlwriter[Date] {
      def ConvertToHtml(something: Date): Unit  ={
        println(s"\n <h1> The name is $something.toSting ")
      }
    }
  }

  import DateTypeClassInstance._

  // 3 - Interface Using implicit parameter

def htmlwriterInterface[A](aType : A)(implicit writer: Htmlwriter[A]) = {
    writer.ConvertToHtml(aType)
}



  htmlwriterInterface(Person1("Bane", 10))
  htmlwriterInterface[Date]( new Date)

}
