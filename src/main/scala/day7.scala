import scala.io.Source
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.*
import scala.util.Try


@main
def main(): Unit = {
  val filename = "/Users/nfitzge4/advent/advent20222/advent2022/inputs/day7.txt"
  val source = Source.fromFile(filename)
  val input = source.getLines.toList.filter(s => s != "$ ls" && !s.startsWith("dir"))

  val root = Node("/", None, Buffer.empty, true, None)
  val parsedRoot = constructNodeTree(input, root)
  val nodeSizes = flatten(List(root)).filter(_.isDir).map(sumSize)
  val part1 = nodeSizes.filter(_ <= 100000).sum
  println(part1)
  val neededSpace = 30000000 - (70000000 - sumSize(root))
  val part2 = nodeSizes.filter(_ >= neededSpace).min
  println(part2)
}

//cant use tailrec here to my eternal shame
def flatten(nodes: List[Node]): List[Node] = {
  nodes match {
    case Nil => List.empty
    case head :: tail =>
      head :: flatten(head.children.toList).appendedAll(flatten(tail))
  }
}

//here neither, the call isn't in tail position
def sumSize(current: Node): Int = {
  if(current.children.isEmpty) {
    current.size.getOrElse(0)
  }
  else {
    current.size.getOrElse(0) + current.children.map(sumSize).sum
  }
}

@tailrec
def findRoot(current: Node): Node =
  current.parent match {
    case None => current
    case Some(parent) => findRoot(parent)
  }

def constructNodeTree(input: List[String], current: Node): Node = {
  val sizeRegex = "(\\d*) (.*)".r
  input match {
    case Nil => current
    case head :: tail =>
      if (head.startsWith("$ cd ..")) {
        constructNodeTree(tail, current.parent.get)
      }
      else if(head.startsWith("$ cd /")) {
        constructNodeTree(tail, findRoot(current))
      }
      else if (head.startsWith("$ cd ")) {
        current.children.find(_.name == head.substring(5)) match {
          case Some(node) => constructNodeTree(tail, node)
          case None =>
            val newNode = Node(head.substring(5), Some(current), Buffer.empty, true, None)
            current.children.append(newNode)
            constructNodeTree(tail, newNode)
        }
      }
      else {
        head match {
          case sizeRegex(size, name) =>
            if(!current.children.exists(_.name == name)) {
              current.children.append(Node(
                name, Some(current), Buffer.empty, false, Some(size.toInt)
              ))
              constructNodeTree(tail, current)
            }
            else {
              constructNodeTree(tail, current)
            }
        }
      }
  }
}

case class Node(name: String, parent: Option[Node], children: mutable.Buffer[Node], isDir: Boolean, size: Option[Int])

