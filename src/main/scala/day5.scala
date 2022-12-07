import scala.io.Source

import scala.annotation.tailrec

@main
def main(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day5.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList
  val pivot = input.zipWithIndex.find(i => i._1.isEmpty).head._2
  val regex = "move (\\d*) from (\\d*) to (\\d*)".r
  val tops = input.drop(pivot).reverse.collect {
    case regex(move, from, to) =>
      (move.toInt, from.toInt - 1, to.toInt - 1)
  }.foldRight(input.take(pivot - 1)
    .map(_.grouped(4).map(_.charAt(1)).toList).transpose.map(_.filter(!_.isSpaceChar)).zipWithIndex.map(x => (x._2, x._1)).toMap)((move, crateMap) => {
    //add a reverse after the take here for part 1
    val crates = crateMap(move._2).take(move._1)
    val afterTake = crateMap.updated(move._2, crateMap(move._2).drop(move._1))
    val afterAdd = afterTake.updated(move._3, crates.concat(afterTake(move._3)))
    afterAdd
  }).toList.sortBy(_._1).map(crates => crates._2.headOption.getOrElse(" ")).mkString("")
  println(tops)
}
/**
this one's dense. first we find the end index of the top block and call that the pivot. we parse that out into lists of lists of characters
then we use the transpose function to just flip them (real convenient). after all that we have a list of the stacks, with the top of the stack first.
the we parse out the move command and fold over it (we're folding the wrong way which is why there's the extra .reverse there). then we just grab the
front of the stack for the source stack and add it to the front of the destination stack.

this would absolutely not be performant on a super large data set because im using scala's default immutable collections.
this also cannot accommodate a starting grid that isn't a perfect square unless i write my own transpose() since apparently that's a limitation there