import scala.math.log
import scala.annotation.tailrec

def flatten1[A] (xss: List[List[A]]): List[A] = {
    @tailrec
    def flattenHelper[A] (xss: List[List[A]], acc: List[A]): List[A] =
        xss match {
            case Nil => acc
            case h :: tl => flattenHelper(tl, acc:::h)
        }
    flattenHelper(xss, List())
}
flatten1(List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)))

def count[A] (x: A, xs: List[A]): Int = {
    @tailrec
    def countHelper[A] (x: A, xs: List[A], n: Int): Int =
        xs match {
            case Nil => n
            case h :: tl => if h == x then countHelper(x, tl, n + 1) else countHelper(x, tl, n)
        }
    countHelper(x, xs, 0)
}
count(1, List(1, 2, 3, 1, 2, 3, 1, 2, 3))

def replicate[A] (x: A, n: Int): List[A] = {
    @tailrec
    def replicateHelper[A] (x: A, n: Int, acc: List[A]): List[A] =
        n match {
            case 0 => acc
            case n => replicateHelper(x, n - 1, acc :+ x)
        }
    replicateHelper(x, n, List())
}
replicate("Hello", 3)

def sqrList(xs: List[Int]): List[Int] = {
    @tailrec
    def sqrHelper (xs: List[Int], acc: List[Int]): List[Int] =
        xs match {
            case Nil => acc
            case h :: tl => sqrHelper(tl, acc :+ (h * h))
        }
    sqrHelper(xs, List())
}
sqrList(List(1, 2, -4, 5))

def sqrList2(xs: List[Int]): List[Int] = 
    if (xs == Nil) Nil
    else xs.head * xs.head :: sqrList2(xs.tail) 
sqrList2(List(1, 2, -4, 5))

def reverse[A] (xs: List[A]): List[A] = {
    @tailrec
    def reverseHelper[A] (xs: List[A], acc: List[A]): List[A] =
        xs match {
            case Nil => acc
            case h :: tl => reverseHelper(tl, h :: acc)
        }
    reverseHelper(xs, List())
}

def palindrome[A] (xs: List[A]): Boolean = reverse(xs) == xs
palindrome(List(1, 2, 3, 2, 1))

def listLength[A](xs: List[A]): Int = {
    @tailrec
    def lengthHelper[A] (xs: List[A], n: Int): Int =
        xs match {
            case Nil => n
            case h :: tl => lengthHelper(tl, n + 1)
        }
    lengthHelper(xs, 0)
}
listLength(List(1, 3, 5))

def recursive (n: Int): Double = if (n == 1) then 1 else recursive(n / 2) + log(n)

recursive(16)