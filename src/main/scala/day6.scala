import scala.io.Source

import scala.annotation.tailrec

val preambleSize = 14


def day6(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day6.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.next().toList

  val part1 = startFinder(input.drop(preambleSize), input.take(preambleSize), preambleSize)
  println(part1)
}

@tailrec
def startFinder(iter: List[Char], prev: List[Char], i: Int): Int = {
  val lel  = prev.tail :+ iter.head
  if(lel.toSet.size == preambleSize) i + 1 else startFinder(iter.tail, lel, i + 1)
}