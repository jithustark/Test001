
//Match Case

def max(a: Int, b: Int) = if (a>=b) a else b
max(2,5)

/*
Compared to the If-else scala programmers would prefer Match_case Statements

 */

def max_case(a:Int, b: Int) = a<=b match{
  case true => a
  case false => b

}

max_case(10, 12)

def pass_fail(marks:Int) = marks>=50  match{
  case true => "Pass"
  case false => "Fail"

}

pass_fail(70)
pass_fail(30)

// Even or Odd

def isEven(x: Int) = x%2 match {
  case 0 => true
  case _ => false
}

isEven(5)
isEven(4)

// Leap Year
def leapyear(year: Int) =
  (year%4 == 0 && year%100 != 0) || year%400 ==0
  match {
    case true => true
    case false => false
  }

leapyear(1998)
leapyear(2004)

// Chained conditions

def sign(x: Int) = {
  if(x>0) 1
  else if (x == 0) 0
  else -1
}

sign(-3)
sign(0)
sign(1)

def sign1(x: Int) = x match {
  case x if x>0 => 1
  case x if x==0 => 0
  case x if x<0 => -1
}
sign1(-3)
sign1(0)
sign1(1)

// Grading System
def grade(marks: Int) = {
  if (marks>= 75) "A"
  else if  (marks>= 65) "B"
  else if (marks>= 50) "C"
  else "F"
}
grade(50)
grade(76)
grade(30)


def grade1(marks: Int) = marks match {
  case marks if (marks>= 75) => "A"
  case marks if (marks>= 65) => "B"
  case marks if (marks>= 50) => "C"
  case _ => "F"
}

grade1(50)
grade1(76)
grade1(30)

// Problems
/*
Develop a Function interest . It consumes a deposit amount and produces the actual amount
of interest that the money earns a year.

The Bank pays a flat 5% for deposits of upto Rs 1000
flat 6% pays 10000
flat 7% pays 100000
flat 8% pays > 100000

 */

def interest(amt: Double) = amt match {
  case x if x<=0 => 0
  case x if x<=1000 => x*.5
  case x if x<=10000 => x*.6
  case x if x<=100000 => x*.7
  case x if x>=100000 => x*.8
}

interest(100000)