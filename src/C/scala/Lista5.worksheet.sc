import scala.collection.immutable.LazyList

// ZAD 1

def lrepeat[A](k: Int)(lxs: LazyList[A]): LazyList[A] = {
    def repeatHelper(c: Int, lxs: LazyList[A]): LazyList[A] =
        (c, lxs) match {
            case (_, l) if l.isEmpty => LazyList.empty
            case (0, h #:: tl) => repeatHelper(k, tl)
            case (c, h #:: tl) => h #:: repeatHelper(c - 1, lxs)
        }
    repeatHelper(k, lxs)
}
lrepeat(2)(LazyList.from(0)).take(10).toList

// ZAD 2

def lfib = {
    def fib(a: Int, b: Int): LazyList[Int] = a #:: fib(b, a + b)
    fib(1, 1)
}
lfib.take(10).toList

// ZAD 3

sealed trait lBT[+A]
case object LEmpty extends lBT[Nothing]
case class LNode[+A](elem: A, left: () => lBT[A], right: () => lBT[A]) extends lBT[A]

val ltt = LNode(1, 
(() => LNode(2,
(() => LNode(4,
(() => LEmpty),
(() => LEmpty))),
(() => LEmpty))), 
(() => LNode(3,
(() => LNode(5,
(() => LEmpty),
(() => LNode(6,
(() => LEmpty),
(() => LEmpty))))),
(() => LEmpty))));;

def lBreadth[A] (ltt: lBT[A]): LazyList[A] = {
    def breadthHelper (q: List[lBT[A]]): LazyList[A] = {
        q match {
            case Nil => LazyList.empty
            case h :: tl =>
                h match {
                    case LEmpty => breadthHelper(tl)
                    case LNode (v, l, r) => v #:: breadthHelper(tl:::List(l(), r()))  
                }
        }
    }
    breadthHelper(List(ltt))
}
lBreadth(ltt).toList

def lTree (n: Int): lBT[Int] = LNode(n, () => lTree (2 * n), () => lTree (2 * n + 1))
lBreadth(lTree(1)).take(20).toList