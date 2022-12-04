import scala.io.Source

import scala.annotation.tailrec

@main
def main(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day3.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList
  val part1Output = part1(input)
  val part2Output = part2(input)
  source.close()

  println(part1Output)
  println(part2Output)
}

def part1(input: List[String]): Int =
  input.map(line => line.splitAt(line.length / 2)).map(lines => List(lines._1, lines._2)).map(_.map(_.toCharArray.map(sub).toSet) match {
    case a :: b :: Nil => a.intersect(b).sum
  }).sum


@tailrec
def part2(iter: List[String], total: Int = 0): Int  =
  iter match {
    case elf1 :: elf2 :: elf3 :: tail =>
      part2(tail, total + sub(List(elf1, elf2, elf3).foldLeft((elf1 + elf2 + elf3).toSet)((acc, elf) => acc.intersect(elf.toSet)).head))
    case _ => total
  }

def sub(c: Char): Int = if(c < 97) c - 38 else c - 96