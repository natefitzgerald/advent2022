import sun.security.ec.point.ProjectivePoint.Mutable

import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.util.Try

/**
 * this one got away from me a bit
 * transpose is still just super sick though
 */


def day8(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day8.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.map(_.toList).toList

  //so what we're doing here is using a function that removes invisible trees \, starting from the left
  //then we just use reverse and transpose to arrange the input so that all four relevant computations can be done in that orientation
  //the we union the sets to get any tree visible in any direction already deduplicated

  def removeInvisible(line: List[Char]): List[Int] =
    line.zipWithIndex.foldLeft(("!", List(-1)))((l, c) => if(c._1 > l._1.last) (l._1 + c._1, c._2 :: l._2 ) else l)._2.reverse.drop(1)

  val part1 = input.zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map((_, y._2))
  }).toSet.union(input.map(_.reverse).zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (y._1.size - xprime - 1, y._2))
  }).toSet).union(input.transpose.zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map((y._2, _))
  }).toSet).union(input.transpose.map(_.reverse).zipWithIndex.flatMap(y => {
    removeInvisible(y._1).map(xprime => (y._2, y._1.size - xprime - 1))
  }).toSet)
  println(part1.size)


  //this is fun, we need all the rows and columns so we just take the rows of the input
  // then transpose input so we get the columns as rows
  val part2 = input.zipWithIndex.flatMap(row => {
    input.transpose.zipWithIndex.map(col => {
      calcScenicScore(col._2, row._2, row._1, col._1)
    })
  }).max
  println(part2)
}


//similar concept - drop all the invisible trees in each orientation and count what remains
def calcScenicScore(xpos: Int, ypos: Int, row: List[Char], col: List[Char]): Int = {
  val tree = row(xpos)
  val one = filter(row.drop(xpos + 1), tree).size
  val two = filter(row.reverse.drop(row.size - xpos), tree).size
  val three = filter(col.drop(ypos + 1), tree).size
  val four = filter(col.reverse.drop(col.size - ypos), tree).size
  one * two * three * four
}

// this is what drops the trees we can't see
@tailrec
def filter(iter: List[Char], tree: Char, filtered: List[Char] = List.empty): List[Char] =
  iter match {
    case Nil => filtered
    case head :: tail =>
      if (head < tree) {
        filter(tail, tree, head +: filtered)
      }
      else {
        head +: filtered
      }
  }
