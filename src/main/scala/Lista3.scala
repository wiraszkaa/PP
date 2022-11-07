package Lista3

def last[A] (xs: List[A]): Option[A] =
    xs match {
        case Nil => None
        case x :: Nil => Some(x)
        case h :: tl => last(tl)
    }


def lastTwo[A] (xs: List[A]): Option[(A, A)] = 
    xs match {
        case Nil => None
        case x :: Nil => None
        case x :: y :: Nil => Some((x, y))
        case h :: tl => lastTwo(tl)
    }

def length[A] (xs: List[A]): Int = {
    def lengthHelper[A] (xs: List[A], n: Int): Int = 
        xs match {
            case Nil => n
            case h :: tl => lengthHelper(tl, (n + 1))
        }
    lengthHelper(xs, 0)
}

def reverse[A] (xs: List[A]): List[A] = {
    def revHelper[A] (xs: List[A], acc: List[A]): List[A] =
        xs match {
            case Nil => acc
            case h :: tl => revHelper(tl, h :: acc)
        }
    revHelper(xs, List())
}

def palindrome[A] (xs: List[A]): Boolean = reverse(xs) == xs

def contains[A] (xs: List[A], x: A): Boolean = 
    xs match {
        case Nil => false
        case h :: tl => if h == x then true else contains(tl, x)
    }

def distinct[A] (xs: List[A]): List[A] = {
    def distinctHelper[A] (xs: List[A], acc: List[A]): List[A] =
        xs match {
            case Nil => acc
            case h :: tl => if contains(acc, h) then distinctHelper(tl, acc) else distinctHelper(tl, acc :+ h)
        }
    distinctHelper(xs, List())
}

def evenIndex[A] (xs: List[A]): List[A] = {
    def evenHelper[A] (xs: List[A], acc: List[A], n: Int): List[A] =
        xs match {
            case Nil => acc
            case h :: tl => if n % 2 == 0 then evenHelper(tl, acc :+ h, n + 1) else evenHelper(tl, acc, n + 1)
        }
    evenHelper(xs, List(), 0)
}

def isPrime (x: Int): Boolean =
    x match {
        case 0 => false
        case 1 => false
        case all => {
            def primeHelper (x: Int, i: Int): Boolean =
                i match {
                    case 1 => true
                    case default => if x % i == 0 then false else primeHelper(x, default - 1)
                }
            if x < 0 then false else primeHelper(all, all - 1)
        }
    }