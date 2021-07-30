package main.scala.taskAssignment

trait Manager {
  def generateCommand: String
}

class TaskManager extends Manager {
  override def generateCommand: String = {
    "Erst die Arbeit, dann das Fergnugen"
  }
}