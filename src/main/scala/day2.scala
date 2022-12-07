import scala.io.Source

import scala.annotation.tailrec

def day2(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day2.txt"
  val source = Source.fromFile(filename)
  val fileContents = source.getLines.map(line => (line.charAt(0), line.charAt(2))).toList
  source.close()

  val p1Result = day2Rec(fileContents, p1LookupTable)
  val p2Result = day2Rec(fileContents, p2LookupTable)
  println(p1Result)
  println(p2Result)
}

val p1LookupTable: Map[Char, Map[Char, Int]] =
  Map('A' -> Map('X' -> 4, 'Y' -> 8, 'Z' -> 3),
      'B' -> Map('X' -> 1, 'Y' -> 5, 'Z' -> 9),
      'C' -> Map('X' -> 7, 'Y' -> 2, 'Z' -> 6)
  )

val p2LookupTable: Map[Char, Map[Char, Int]] =
  Map('A' -> Map('X' -> 3, 'Y' -> 4, 'Z' -> 8),
      'B' -> Map('X' -> 1, 'Y' -> 5, 'Z' -> 9),
      'C' -> Map('X' -> 2, 'Y' -> 6, 'Z' -> 7)
  )

//probably could have just done this as a fold instead
@tailrec
def day2Rec(in: List[(Char, Char)], lookupTable: Map[Char, Map[Char, Int]], total: Int = 0): Int =
  in match {
    case Nil => total
    case head :: tail =>
      day2Rec(tail, lookupTable, total + lookupTable(head._1)(head._2))
  }
