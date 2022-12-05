// ZAD 1

def whileLoop(p: => Boolean)(f: => Unit): Unit = {
  if (p) {
    f
    whileLoop(p)(f)
  }
}

var counter = 0;
whileLoop(counter < 3)({
  println(counter)
  counter = counter + 1
})

// ZAD 2

def swap[A](xs: Array[A], i: Int, j: Int) = {
    val temp = xs(i);
    xs(i) = xs(j);
    xs(j) = temp;
}

val xs = Array(1, 2, 3, 4, 5)
swap(xs, 0, 1)

def choosePivot[A](tab: Array[A], m: Int, n: Int) = tab((m + n) / 2);

def partition(tab: Array[Int], l: Int, r: Int): (Int, Int) = {
    var i = l
    var j = r
    val pivot = choosePivot(tab, l, r)
    while (i <= j) {
      while (tab(i) < pivot) i += 1
      while (pivot < tab(j)) j -= 1
      if (i <= j) {
        swap(tab, i, j)
        i += 1
        j -= 1
      }
    }
    (i, j)
}

def quick(tab: Array[Int], l: Int, r: Int): Unit =
    if (l < r) {
        val (i, j) = partition(tab, l, r)
        if ((j - l) < (r - i)) {
            quick(tab, l, j)
            quick(tab, i, r)
        } else {
            quick(tab, i, r)
            quick(tab, l, j)
        }
    } else ()

def quickSort(tab: Array[Int]) = quick(tab, 0, tab.length - 1)

var arr: Array[Int] = Array(1, 2, 4, 3, 13, 45, 6);
quickSort(arr);
println(arr.toList)
