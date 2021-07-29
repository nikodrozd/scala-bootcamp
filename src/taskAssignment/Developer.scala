package taskAssignment

trait Developer {
  var currentTask: String
  def startOfWork()
}

class TaskDeveloper(val name: String) extends Developer {

  override var currentTask: String = _

  override def startOfWork(): Unit ={
    println(s"taskAssignment.Developer $name started task $currentTask.")
  }
}