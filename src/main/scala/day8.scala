import sun.security.ec.point.ProjectivePoint.Mutable

import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.util.Try


@main
def day8(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day8.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.map(_.toList).toList


  val part1 = input.zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (xprime, y._2))
  }).toSet.union(input.map(_.reverse).zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (y._1.size - xprime - 1, y._2))
  }).toSet).union(input.transpose.zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (y._2, xprime))
  }).toSet).union(input.transpose.map(_.reverse).zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (y._2, y._1.size - xprime - 1))
  }).toSet)

  println(part1.size)


  val g = input.zipWithIndex.flatMap(row => {
    input.transpose.zipWithIndex.map(col => {
      calcScenicScore(col._2, row._2, row._1, col._1)
    })
  }).max
  println(g)
}



def removeInvisible(line: List[Char]): List[Int] = {
  line.zipWithIndex.foldLeft(("!", List(-1)))((l, c) => if(c._1 > l._1.last) (l._1 + c._1, c._2 :: l._2 ) else l)._2.reverse.drop(1)
}

def calcScenicScore(xpos: Int, ypos: Int, row: List[Char], col: List[Char]): Int = {

  val tree = row(xpos)


  val one1 = filter(row.drop(xpos), tree)
  println(one1)
  val one = one1.size
  val two = filter(row.reverse.drop(xpos), tree).size
  val three = filter(row.drop(ypos), tree).size
  val four = filter(row.reverse.drop(ypos), tree).size



  val x = one * two * three * four

    x
}

@tailrec
def filter(iter: List[Char], tree: Char, filtered: List[Char] = List.empty): List[Char] = {
  iter match {
    case Nil => filtered
    case head :: tail => {
      println(iter.size)
      if (head < tree) {
        filter(tail, tree, head +: filtered)
      }
      else {
         head +: filtered
      }
    }
  }
}