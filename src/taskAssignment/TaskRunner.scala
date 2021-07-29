package taskAssignment

object TaskRunner {
  val taskList: List[Task] = List(PreparationTask, PresentationTask, ReviewTask, SupportTask)
  val devList: List[Developer] = List("dev1", "dev2", "dev3", "dev4").map(TaskDeveloper.apply)

  def main(args: Array[String]) {

    val manager: Manager = new TaskManager
    for (i <- 1 to 14) {
      println()
      println(s"Current lecture is $i")
      println(manager.generateCommand)
      assignTasks()
      Thread.sleep(1000)
    }

  }

  def assignTasks(): Unit = {
    val shuffledDevList = scala.util.Random.shuffle(devList)
    for (j <- taskList.indices) {
      println(shuffledDevList(j).startOfWork(taskList(j).taskName))
    }
  }

}
