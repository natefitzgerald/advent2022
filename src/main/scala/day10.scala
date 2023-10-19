import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.HashSet

def day10(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day10.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList

  val states = go(input, 1, List(1))

  val keynums = List(19, 59, 99, 139, 179, 219)
  //val sum = states.zipWithIndex.filter(x => keynums.contains(x._2)).map(x => x._1 * (x._2 + 1)).sum
  //println(sum)

  val outputStr = printLines(input)
  outputStr.toList.grouped(40).map(_.mkString).foreach(println)
}

@tailrec
def go(lines: List[String], current: Int, states: List[Int]): List[Int] = {
  lines match {
    case Nil => states
    case line :: tail if line.startsWith("addx") => go(tail, current + line.substring(5).toInt, states concat List(current, current + line.substring(5).toInt))
    case _ :: tail  => go(tail, current, states :+ current)
  }
}

def printLines(input: List[String], position: Int = 1, acc: String = ""): String = {
  input match {
    case Nil => acc
    case head :: tail if head == "noop" =>
      val newLine = acc + (if(Math.abs(position - (acc.length % 40)) < 2) "#" else ".")
      printLines(tail, position, newLine)

    case head :: tail =>
      val newLine = acc + (if(Math.abs(position - (acc.length % 40)) < 2) "#" else ".")
      val secondNewLine = newLine + (if(Math.abs(position - (newLine.length % 40)) < 2) "#" else ".")
      val newPosition = position + head.substring(5).toInt
      printLines(tail, newPosition, secondNewLine)
  }
}