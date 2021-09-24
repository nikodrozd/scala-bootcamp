package histogramactors


/**
 * Master actor actions
 */
object Start
case class LoadResult(dataFromFile: Seq[String])
object MappingFinished
object GetReduceResult
case class FailResult(ex: Throwable)
object SuccessResult

/**
 * Loader actor actions
 */
case class Load(inputFilePath: String)

/**
 * Mapper actor actions
 */
case class DocumentToMap(dataFromFile: Seq[String])

/**
 * ParserPool actor actions
 */
case class Block(block: Seq[String])

/**
 * Reducer actor actions
 */
case class AddToResult(map: Map[String, Int])
case class ReduceResult(result: Seq[(String, Int)])

/**
 * Saver actor actions
 */
case class Save(path: String, result: Seq[(String, Int)])


