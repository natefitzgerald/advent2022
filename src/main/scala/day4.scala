import scala.io.Source

import scala.annotation.tailrec


def day4(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day4.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toSeq

  //val part1Output = day4p1(input)
 // println(part1Output)
  val part2Output = day4Part1Final(input)
  println(part2Output)
  source.close()
}

//first draft at this problem - probably more efficint on the machine but just ugly u no?
def day4p1a(input: Seq[String]): Int = {
  input.map(line => line.split(',').toList.flatMap(pair => pair.split('-').toList.map(num => num.toInt))).map {
    case x1 :: x2 :: y1 :: y2 :: Nil =>
      val g = List((x1, x2), (y1, y2)).sortBy(l => l._2 - l._1)
      g.tail.head._1 <= g.head._1 && g.tail.head._2 >= g.head._2
  }.count(identity)
}

//i wrestled with the compiler for a while to try to get this down to a tuple instead of a list because I think it looks dumb this way
// but it just wouldnt work and I eventually gave up
def day4Part1Final(input: Seq[String]) = {
  inputHelper(input,
    (ranges: List[Set[Int]]) => ranges.head.intersect(ranges.tail.head).size == Math.min(ranges.head.size, ranges.tail.head.size))
}

def day4Part2Final(input: Seq[String]) = {
  inputHelper(input,
    (ranges: List[Set[Int]]) => ranges.head.intersect(ranges.tail.head).nonEmpty)
}

def inputHelper(input: Seq[String], f: List[Set[Int]] => Boolean): Int = {
  input.map(line => line.split(',').toList.flatMap(pair => pair.split('-').toList.map(num => num.toInt))).map {
    case x1 :: x2 :: y1 :: y2 :: Nil =>
      f(List(List.range(x1, x2 + 1).toSet, List.range(y1, y2 + 1).toSet))
  }.count(identity)
}