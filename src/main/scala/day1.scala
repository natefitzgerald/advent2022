import scala.io.Source
import scala.io.Source.*
import scala.annotation.tailrec

//number of elves to find the max for
val n = 3

def day1(): Unit = {

  val filename = "/Users/nfitzge4/advent/advent20222/inputs/day1.txt"
  val source = Source.fromFile(filename)
  val fileContents = source.getLines.map(c => c.toIntOption).toList
  source.close()

  println(acc(fileContents, List.fill(n)(0)).sum)
}

@tailrec
def acc(iter: List[Option[Int]], maxSubSum: List[Int], currentSubSum: Int = 0): List[Int] =
  iter match {
    case Nil => maxSubSum
    case head :: tail =>
      head match {
        case None =>
          if (currentSubSum > maxSubSum.min)
            //this could be improved so it doesn't need to sort at each step
            acc(tail, maxSubSum.sorted.reverse.take(n - 1).::(currentSubSum))
          else acc(tail, maxSubSum)

        case Some(v) =>
          acc(tail, maxSubSum, currentSubSum + v)
      }
  }

