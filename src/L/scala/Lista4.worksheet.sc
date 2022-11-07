def map[A, B] (xs: List[A], f: A => B): List[B] = {
    def mapHelper[A, B] (xs: List[A], f: A => B, acc: List[B]): List[B] =
        xs match {
            case Nil => acc
            case h :: tl => mapHelper(tl, f, acc ::: List(f(h)))
        }
    mapHelper(xs, f, List())  
}
map(List(1, 2, 3, 4), x => x * x)

def filter[A](xs: List[A], f: A => Boolean): List[A] = {
    def filterHelper[A](xs: List[A], f: A => Boolean, acc: List[A]): List[A] =
        xs match {
            case Nil => acc
            case h :: tl => if f(h) then filterHelper(tl, f, acc ::: List(h)) else filterHelper(tl, f, acc)
        }
    filterHelper(xs, f, List())
}
filter(List(1, 2, 3, 4), x => x > 2)

def + (x: Int, y: Int): Int = x + y
def reduce[A, B](xs: List[A], f: (A, B) => B, acc: B): B =
    xs match {
        case Nil => acc
        case h :: tl => reduce(tl, f, f(h, acc))
    }
reduce(List(1, 2, 3, 4), +, 0)

def average(xs: List[Int]): Float = {
    def averageHelper(xs: List[Int], s: Int, l: Int): Float =
        xs match {
            case Nil => s.toFloat / l.toFloat
            case h :: tl => averageHelper(tl, s + h, l + 1)
        }
    averageHelper(xs, 0, 0)
}
average(List(1, 2, -2, 2, 7))

def short(s: String): String =
    s.trim() match {
        case "" => ""
        case s => s.split(" ").map(s => s.substring(0, 1)).mkString
    }
short("  Zakład Ubezpieczeń Społecznych ")

def sqrFilter(xs: List[Int]) = {
    val sum = reduce(xs, +, 0)
    map(filter(xs, x => math.pow(x, 3) < sum), x => math.pow(x, 2))
}
sqrFilter(List(1, 2, 3, 4))