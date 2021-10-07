package histogramactors


/**
 * Master actor actions
 */
object DoTheJob
case class NumberOfBlocks(numberOfBlocks: Int)

/**
 * Parser actor actions
 */
case class Block(block: Seq[String])

/**
 * Reducer actor actions
 */
case class AddToResult(map: Map[String, Int])
case class ReduceResult(result: Seq[(String, Int)])


