import scala.io.StdIn.readLine
import Lista3.*
import UI.*

@main def hello: Unit = {
  lista3
}

def msg = "I was compiled by Scala 3. :)"

def lista3: Unit = {
  var continue = true 
  while(continue) {
    println("1 - last\n2 - lastTwo\n3 - length\n4 - palindrome\n5 - distinct\n6 - evenIndex\n7 - isPrime\n0 - exit")
    println("Enter option: ")
    val option = readLine()
    option match {
      case "1" => println("Last element: " + last(createIntList()))
      case "2" => println("Last two elements: " + lastTwo(createIntList()))
      case "3" => println("Length: " + length(createIntList()))
      case "4" => println("isPalindrome: " + palindrome(createIntList()))
      case "5" => println("Distinct list: " + distinct(createIntList()))
      case "6" => println("List from even indexes: " + evenIndex(createIntList()))
      case "7" => println("isPrime: " + isPrime(userInt()))
      case _ => continue = false
    }
    println()
  }
}