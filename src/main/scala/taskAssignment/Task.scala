package taskAssignment

trait Task {
  val taskName: String
}

object PreparationTask extends Task {
  override val taskName: String = "Materials preparation"
}

object PresentationTask extends Task {
  override val taskName: String = "Presentation"
}

object ReviewTask extends Task {
  override val taskName: String = "Homework review"
}

object SupportTask extends Task {
  override val taskName: String = "Q&A session support"
}