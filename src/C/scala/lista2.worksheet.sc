import scala.annotation.tailrec

// EvenR(3) -> OddR(2) -> EvenR(1) -> OddR(0) = false

def fib (x: Int): Int =
    x match {
        case 0 => 0
        case 1 => 1
        case x => fib(x - 1) + fib(x - 2)
    }
fib(10)

def fibTail (x: Int): Int = {
    @tailrec
    def fibHelper (x: Int, a: Int, b: Int): Int =
        x match {
            case 0 => a
            case 1 => b
            case x => fibHelper(x - 1, b, a + b)
        }
    fibHelper(x, 0, 1)
}
fibTail(10)

val epsilon = 1.0e-15
def root3 (x: Double): Double = {
    @tailrec
    def rootHelper (x: Double, a: Double): Double = 
        if math.abs(math.pow(x, 3) - a) <= epsilon * math.abs(x)
        then x else rootHelper(x + (a / math.pow(x, 2) - x) / 3, a)
    if x > 1.0 then rootHelper(x / 3, x) else rootHelper (x, x)
}

root3(8)

val List(_, _, x1, _, _) = List(-2, -1, 0, 1, 2)
val List(_, (x2, _)) = List((1, 2), (0, 1))

def initSegment[A] (x: List[A], y: List[A]): Boolean =
    (x, y) match {
        case (Nil, _) => true
        case (_, Nil) => false
        case (h1 :: tl1, h2 :: tl2) => if h1 == h2 then initSegment(tl1, tl2) else false
    }
initSegment(List(1, 2, 4), List(1, 2, 4, 6, 1))

def replaceNth[A] (xs: List[A], n: Int, x: A): List[A] = {
    def replaceHelper[A] (xs: List[A], n: Int, x: A, acc: List[A]): List[A] =
        n match {
            case 0 => acc ::: List(x) ::: xs.tail
            case n => replaceHelper(xs.tail, n - 1, x, acc ::: List(xs.head))
        }
    replaceHelper(xs, n, x, List())
}
replaceNth(List(1, 2, 3, 4), 3, 0)