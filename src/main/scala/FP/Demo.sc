//Pure Functions
val pi = math.Pi

def volume(r: Double) = 4.0/3.0*pi*r*r*r

volume(5)
printf("%.2f", volume(5))

def easy(K:Int) = 8*K
def tempo(k:Int): Int = 7*k
easy(2)+tempo(3)+easy(2)

def bookprice(x:Int) = x*24.95
def discount(x:Double) = x*0.4
def shippingcost(x:Int) = if(x<=50) 3 else 3+(x-50)*.75
shippingcost(50)
shippingcost(49)
shippingcost(51)
bookprice(65)-discount(bookprice(65))+shippingcost((65))

