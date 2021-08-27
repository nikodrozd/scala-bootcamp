package collections.dataAnalysis

object WorkStatus extends Enumeration {
  type WorkStatus = Value
  val Working, NotWorking, NotEmployable = Value
}

object Gender extends Enumeration {
  type Gender = Value
  val Male, Female = Value
}

object AgePeriod extends Enumeration {
  type AgePeriod = Value
  val Young, Active, Elder = Value
}