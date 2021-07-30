package taskAssignment

trait Developer {
  def startOfWork(taskName: String): String
}

case class TaskDeveloper(name: String) extends Developer {

  override def startOfWork(taskName: String): String ={
    s"main.scala.taskAssignment.Developer $name started task $taskName."
  }
}