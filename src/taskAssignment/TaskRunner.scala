package taskAssignment

object TaskRunner {
  val taskList: List[String] = List("materials preparation", "presentation", "homework review", "Q&A session support")
  var devList: List[Developer] = List(new TaskDeveloper("dev1"), new TaskDeveloper("dev2"), new TaskDeveloper("dev3"), new TaskDeveloper("dev4"))

  def main(args: Array[String]) {

    val manager: Manager = new TaskManager
    for (i <- 1 to 14) {
      println(s"Current lecture is $i")
      println(manager.generateCommand)
      assignTasks()
      Thread.sleep(1000)
    }

  }

  def assignTasks(): Unit = {
    val shuffledDevList = scala.util.Random.shuffle(devList)
    for (j <- 0 until taskList.length) {
      shuffledDevList(j).currentTask = taskList(j)
      shuffledDevList(j).startOfWork()
    }
  }

}
