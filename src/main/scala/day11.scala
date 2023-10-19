import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.HashSet



@main
def day11(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day11.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList



  def parseInputForMonkeys(input: List[List[String]], monkeys: List[Monkey]): List[Monkey] = {
    input match {
      case Nil => monkeys
      case head :: tail => // new monkey
        val headArr = head.toArray
        val monkeyNo = headArr(0).replace("Monkey ", "").replace(":", "").toInt
        val items = head(1).replace("  Starting items: ", "").split(", ").toList.map(_.toInt)
        val op = parseOp(head(2))
        val test = parseTest(head(3))
        val trueToss = head(4).replace("    If true: throw to monkey ", "").toInt
        val falseToss = head(5).replace("    If false: throw to monkey ", "").toInt
        val newMonkey = Monkey(monkeyNo, items, op, test, trueToss, falseToss)
        parseInputForMonkeys(tail, monkeys :+ newMonkey)
    }
  }

  def parseOp(opstr: String): Int => Int = {
    val trimmed = opstr.replace("  Operation: new = ", "")
    (x: Int) => {
      val subbed = trimmed.replace("old", x.toString)
      val lhs = subbed.split(" ").head.toInt
      val rhs = subbed.split(" ").reverse.head.toInt
      subbed.split(" ").drop(1).head match {
        case "+" => lhs + rhs
        case "-" => lhs - rhs
        case "/" => lhs / rhs
        case "*" => lhs * rhs
      }
    }
  }

  def parseTest(testStr: String): Int => Boolean = {
    val trimmed = testStr.replace("  Test: ", "")
    (x: Int) => {
      if(trimmed.startsWith("  Test: divisible by "))
        val divisor = trimmed.replace("  Test: divisible by ", "").toInt
        if(x % divisor == 0) true else false
      else ???
    }
  }

  case class Monkey(
                   monkey: Int,
                   items: List[Int],
                   op: Int => Int,
                   test: Int => Boolean,
                   trueToss: Int,
                   falseToss: Int
                   )

  case class Item(
                 heldBy: Int,
                 worry: Int
                 )
  val x = input.grouped(7).toList
  val g = parseInputForMonkeys(x, List.empty)

  val itemDict = g.map(monkey => (monkey.monkey, monkey.items)).toMap

  @tailrec
  def toss(monkeysRemaining: List[Monkey], worry: Int, itemDict: Map[Int, List[Int]]): Map[Int, List[Int]] = {

    monkeysRemaining match {
      case Nil => itemDict
      case monkey :: tail => {

      }
    }
  }

  g.foreach(println)
}
