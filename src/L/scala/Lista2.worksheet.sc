2 + 2
4.5 * 3
2 / 3
2.0 / 3.0


val x = 10
var y = 10
y = 20
println(y)


def power2(x: Double): Double = x * x
println(power2(2.2))

def ratio(x: Double, y: Double): Double = x / y
println(ratio(2.0, 3.0))

def max(x: Double, y: Double): Double = x.max(y)
println(max(1.0, -5.0))


def test1(x: Int): Boolean = x >= 0
println(test1(-2 + 2))

def test2(x: Int): Boolean = x <= 0
println(test2(-2 + 3))

def test3(x: Int): Boolean = x % 2 == 0
println(test3(-2))

def func(test: Int => Boolean, x: Int, y: Int, z: Int): Int = {
    var list: List[Int] = List(x,y,z)
    list = list.filter(test)
    return list.sum
}
println(func(test1, -2, 2, 3))
println(func(test2, 2, -10, 3))
println(func(test3, -2, 1, 3))


var friendNames: List[String] = List("Damian", "Dawid", "Maja", "Martyna", "Gracjan", "Borys")

def addFriend(list: List[String], name: String): List[String] = List(name) ::: list
println(addFriend(friendNames, "Philip"))