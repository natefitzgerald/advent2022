

import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.util.Try

/**
def day9(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day9.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList

  val unrolledInput = input.flatMap(l => List.fill(l.drop(2).toInt)(l.head)).reverse

  val tailLocs = unrolledInput.foldRight(List(((0,0), (0, 0))))((input, positions) => {
    val prev = positions.last
    val newHead = calcNewHead(prev._1, input)
    val newTail = calcNewTail(newHead, prev._2)
    val newLocs = (newHead, newTail)
    positions.appended(newLocs)
  })
  println(tailLocs.map(_._2).toSet.size)

  val cheatyList: mutable.Buffer[(Int, Int)] = mutable.Buffer.empty
  val x = List.fill(8)((0,0))
  val part2 = unrolledInput.foldRight(x)((y, z) => {
    val b = lel(z, y)
    println(b.mkString(","))
    cheatyList.append(b(8))
    b
  })

  println(cheatyList.mkString(","))
  println(cheatyList.toSet.size)
}

def lel(locs: List[(Int, Int)], input: Char): List[(Int, Int)] = {
  val newHead = calcNewHead(locs.head, input)
  newHead :: pairwise(newHead :: locs.take(8))
}

def pairwise(seq: List[(Int, Int)]): List[(Int, Int)] = {
  seq match {
    case head :: tail :: rest =>
      val newTail = calcNewTail(head, tail)
      newTail :: pairwise(newTail :: rest)
    case _ => List()
  }
}

object helpers {
  def calcNewHead(prevHead: (Int, Int), input: Char): (Int, Int) = {
    if     (input == 'L') (prevHead._1 - 1, prevHead._2)
    else if(input == 'R') (prevHead._1 + 1, prevHead._2)
    else if(input == 'D') (prevHead._1, prevHead._2 - 1)
    else                  (prevHead._1, prevHead._2 + 1)
  }

  def calcNewTail(newHead: (Int, Int), prevTail: (Int, Int)): (Int, Int) = {
      if(newHead._1 - prevTail._1 == 2) (prevTail._1 + 1, newHead._2)
      else if(prevTail._1 - newHead._1 == 2) (prevTail._1 - 1, newHead._2)
      else if(newHead._2 - prevTail._2 == 2) (newHead._1, prevTail._2 + 1)
      else if(prevTail._2 - newHead._2 == 2) (newHead._1, prevTail._2 - 1)
      else prevTail
  }

  def calcNewTail2(newHead: (Int, Int), prevTail: (Int, Int)): (Int, Int) = {
    //they are in the same row or column but are 2 apart
    if(newHead._1 - prevTail._1 == 2 && newHead._2 == prevTail._2) (prevTail._1 + 1, newHead._2)
    else if(prevTail._1 - newHead._1 == 2 && newHead._2 == prevTail._2) (prevTail._1 - 1, newHead._2)
    else if(newHead._2 - prevTail._2 == 2 && newHead._1 == prevTail._1) (newHead._1, prevTail._2 + 1)
    else if(prevTail._2 - newHead._2 == 2 && newHead._1 == prevTail._1) (newHead._1, prevTail._2 - 1)
    // they are 2 apart but not in the same row or column
    else if(newHead._1 - prevTail._1 >= 2 && newHead._2 - prevTail._2 >= 1) (prevTail._1 + 1, newHead._2 + 1)
    else if(newHead._1 - prevTail._1 >= 2 && prevTail._2 - newHead._2 >= 1) (prevTail._1 + 1, newHead._2 - 1)
    else if(newHead._2 - prevTail._2 >= 1 && newHead._2 - prevTail._2 >= 2) (prevTail._1 + 1, newHead._2 + 1)
    else if(newHead._2 - prevTail._2 >= 1 && newHead._2 - prevTail._2 >= 2) (prevTail._1 + 1, newHead._2 + 1)
    else prevTail
  }

  val lookup: Map[(Int, Int), (Int, Int)] = Map(
    (0, 0) -> (0, 0),

    (1, 0) -> (0, 0),
    (0, 1) -> (0, 0),
    (1, 1) -> (0, 0),
    (-1, 0)  -> (0, 0),
    (0, -1) -> (0, 0),
    (-1, -1) -> (0, 0),

    (-2, 0) -> (1, 0),
    (2, 0) -> (-1, 0),
    (0, -2) -> (0, 1),
    (0, 2) -> (0, -1),

    (-2, -1) -> ()
  )
}
**/

