def blast(n: Int): Unit = {
  println(n)
  if (n<=1) println("BlastOff !!")
  else blast(n-1)
}

blast(3)

/*
2Types of Recursion
-> Direct Recursion
-> InDirect Recursion

Direct Recursion:
- Tail Recursion: we call recursion at the ending
- Head Recrsion: we call recursion at the starting

Multiple Recursion
-Binary Recursion: recursion that is called only once
-Nested Recursion: recursion that is called more than once
 */

def power(x: Int,  y: Int): Int = y match {
  case 0 => 1
  case _ => x * power(x, y-1)

}
println(power(3,5))

def fact(x: Int): BigInt = x match {
  case 1 => x
  case _ => x*fact(x-1)
}
fact(720)

def fibo(n: Int): Int = n match{
  case x if(x ==0) => 0
  case x if(x ==1) => 1
  case _ => fibo(n-1) + fibo(n-2)
}

fibo(10)