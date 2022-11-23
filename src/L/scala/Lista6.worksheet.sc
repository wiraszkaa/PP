import scala.collection.mutable.HashMap
import scala.annotation.tailrec

// ZAD1
def stirling (n: Int, m: Int): Int =
    (n, m) match {
        case (_, 1) => 1
        case (n, m) if n == m => 1
        case (n, m) => stirling (n - 1, m - 1) + m * stirling (n - 1, m)
    }


def memoized_stirling (n: Int, m: Int): Int = {
    val memory = new HashMap[(Int, Int), Int]()
    def memoHelper (n: Int, m: Int, memo: HashMap[(Int, Int), Int]): Int =
        (n, m) match {
            case (_, 1) => 1
            case (n, m) if n == m => 1
            case (n, m) => if memo.contains((n, m)) then memo.getOrElse((n, m), -1)
            else {
                val result = memoHelper (n - 1, m - 1, memo) + m * memoHelper (n - 1, m, memo)
                memo.addOne((n, m), result)
                result
            }
        }
    memoHelper(n, m, memory)
}
stirling(100, 5)
memoized_stirling(100, 5)

// ZAD2
def make_memoize[A] (f: A => A): (A => A) = {
    val memory = new HashMap[A, A]()
    (x: A) => if memory.contains(x) then memory.getOrElse(x, x) else {
        val result = f(x)
        memory.addOne(x, result)
        result
    }
}

def fib (x: Int): Int =
    x match {
        case 0 => 0
        case 1 => 1
        case x => fib(x - 1) + fib(x - 2)
    }
val memoized_fib = make_memoize(fib)
memoized_fib(6)

// ZAD3
lazy val lazy_s = stirling (200, 7)
println("Hello, It's working")
// println(lazy_s)

// ZAD4
sealed trait Sequence[A]
case class Cons[A](elem: A, f: () => Cons[A]) extends Sequence[A]

def natural (n: Int): Cons[Int] = Cons(n, () => natural(n + 1))
def bell (n: Int, k: Int): Cons[Int] = Cons(stirling(n, k), () => bell(n + 1, k))

def stream_head[A] (s: Cons[A]): A =
    s match {
        case Cons(h, t) => h
    }
def stream_thunk[A] (s: Cons[A]): (() => Cons[A]) =
    s match {
        case Cons(h, t) => t
    }

def stream_list[A] (s: Cons[A], n: Int): List[A] = {
    @tailrec
    def listHelper[A] (s: Cons[A], n: Int, acc: List[A]): List[A] = {
        n match {
            case 0 => acc
            case n => listHelper (stream_thunk(s)(), n - 1, acc :+ stream_head(s))
        }
    }
    listHelper(s, n, List())
}
stream_list(bell(2, 2), 4)

def stream_odd_list[A] (s: Cons[A], n: Int): List[A] = {
    val m = n * 2
    @tailrec
    def listHelper[A] (s: Cons[A], n: Int, acc: List[A]): List[A] = {
        n match {
            case 0 => acc
            case n => if n % 2 == 0 then 
                listHelper (stream_thunk(s)(), n - 1, acc :+ stream_head(s)) else
                    listHelper (stream_thunk(s)(), n - 1, acc)           
        }
    }
    listHelper(s, m, List())
}
stream_odd_list(bell(2, 2), 4)

def stream_pop[A] (s: Cons[A], n: Int): Cons[A] =
    n match {
        case 0 => s
        case n => stream_pop(stream_thunk(s)(), n - 1)
    }
stream_list(stream_pop(bell(2, 2), 2), 4)

def stream_combine[A, B] (s1: Cons[A], s2: Cons[B], n: Int): List[(A, B)] = {
    @tailrec
    def combineHelper[A, B] (s1: Cons[A], s2: Cons[B], n: Int, acc: List[(A, B)]): List[(A, B)] =
        n match {
            case 0 => acc
            case n => combineHelper(stream_thunk(s1)(), stream_thunk(s2)(), n - 1, acc :+ (stream_head(s1), stream_head(s2))) 
        }
    combineHelper(s1, s2, n, List())
}
stream_combine(natural(1), bell(2, 2), 4)