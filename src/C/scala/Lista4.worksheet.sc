import scala.annotation.tailrec

// ZAD 3

sealed trait BT[A]
case class Empty[A]() extends BT[A]
case class Node[A](elem: A, left: BT[A], right: BT[A]) extends BT[A]

val tt = Node(
  1,
  Node(2, Node(4, Empty(), Empty()), Empty()),
  Node(3, Node(5, Empty(), Node(6, Empty(), Empty())), Empty())
)

def breadthBT[A](tt: BT[A]): List[A] = {
  @tailrec
  def breadthHelper[A](q: List[BT[A]], acc: List[A]): List[A] =
    q match {
      case Nil => acc
      case h :: tl =>
        h match {
          case Empty()       => breadthHelper(tl, acc)
          case Node(v, l, r) => breadthHelper(tl ::: List(l, r), v :: acc)
        }
    }
  breadthHelper(List(tt), List()).reverse
}
breadthBT(tt)

// ZAD 4

def inPath[A](tt: BT[A]): Int = {
  def pathHelper(tt: BT[A], n: Int): Int =
    tt match {
      case Empty()       => 0
      case Node(_, l, r) => n + pathHelper(l, n + 1) + pathHelper(r, n + 1)
    }
  pathHelper(tt, 0)
}
inPath(tt)

def outPath[A](tt: BT[A]): Int = {
  def pathHelper(tt: BT[A], n: Int): Int =
    tt match {
      case Empty()       => n
      case Node(_, l, r) => pathHelper(l, n + 1) + pathHelper(r, n + 1)
    }
  pathHelper(tt, 0)
}
outPath(tt)

// ZAD 5

sealed trait Graphs[A]
case class Graph[A](succ: A => List[A]) extends Graphs[A]

val g = Graph((n) =>
  n match {
    case 0 => List(3)
    case 1 => List(0, 2, 4)
    case 2 => List(1)
    case 3 => List()
    case 4 => List(0, 2)
    case n => throw new Exception(s"Graph g: node $n doesn't exist")
  }
)

def depthSearch[A] (g: Graph[A], v: A): List[A] = {
    @tailrec
    def searchHelper[A] (g: Graph[A], q: List[A], acc: List[A]): List[A] =
        q match {
            case Nil => acc
            case h :: tl => if acc.contains(h) then searchHelper(g, tl, acc) else searchHelper(g, (g succ h):::tl, h::acc)
        }
    searchHelper(g, List(v), List()).reverse
}
depthSearch(g, 4)