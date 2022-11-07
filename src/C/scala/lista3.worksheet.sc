def curry3[A, B, C, D] (f: A => B => C => D) = (x: A, y: B, z: C) => f (x) (y) (z)
def uncurry3[A, B, C, D] (f: (A, B, C) => D) = (x: A) => (y: B) => (z: C) => f (x, y, z)

def sumProd (xs: List[Double]): (Double, Double) = 
    xs.foldLeft((0.0, 1.0))((acc, x) => (acc._1 + x, acc._2 * x))
sumProd(List(1, 2, 3, 4, 5))

def insertionsort[A] (f: (A, A) => Boolean, xs: List[A]): List[A] = {
    def insert[A] (x: A, xs: List[A], f: (A, A) => Boolean): List[A] =
        xs match {
            case Nil => List(x)
            case h :: tl => if f(x, h) then x :: h :: tl else h :: insert(x, tl, f)
        }
    xs.foldLeft(List[A]())((acc, x) => insert(x, acc, f))
}
insertionsort((x: Int, y: Int) => (x <= y), List(1, 3, 2, 5, 4, 0))

def mergesort[A] (f: (A, A) => Boolean, xs: List[A]): List[A] = {
    def merge[A] (xs1: List[A], xs2: List[A], f: (A, A) => Boolean): List[A] =
        (xs1, xs2) match {
            case (Nil, _) => xs2
            case (_, Nil) => xs1
            case (h1 :: tl1, h2 :: tl2) => if f(h1, h2) then h1 :: merge(tl1, xs2, f) else h2 :: merge(xs1, tl2, f)
        }
    def split[A] (xs: List[A], m: Int): (List[A], List[A]) = {
        def splitHelper[A] (acc: List[A], m: Int, xs: List[A]): (List[A], List[A]) =
            m match {
                case 0 => (acc.reverse, xs)
                case m => splitHelper(xs.head :: acc, m - 1, xs.tail)
            }
        splitHelper(List[A](), m, xs)
    }
    xs match {
        case Nil => Nil
        case x :: Nil => List(x)
        case xs => {
            val (l, r) = split(xs, xs.length / 2)
            merge(mergesort(f, l), mergesort(f, r), f)
        }
    }
}
mergesort((x: Int, y: Int) => (x <= y), List(1, 3, 2, 5, 4, 0))