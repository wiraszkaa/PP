package UI
import scala.io.StdIn.readLine

def userInt (): Int = {
    println("Enter Integer: ")
    return readLine().toInt
}

def createIntList (): List[Int] = {
    println("Creating Integer list...")
    var continue = true
    var list: List[Int] = List()
    var counter = 1
    while (continue) {
        println("Enter " + counter + ". number[q - quit]: ")
        val option = readLine()
        if option == "q" then continue = false else list = list :+ option.toInt

        counter = counter + 1
    }
    print("Created: ")
    println(list)

    return list
}

def createStringList (): List[String] = {
    println("Creating String list...")
    var continue = true
    val list = List()
    while (continue) {
        println("Number[q - quit]: ")
        var option = readLine()
        if option == "q" then continue = false else list :+ option
    }
    print("Created: ")
    println(list)

    return list
}