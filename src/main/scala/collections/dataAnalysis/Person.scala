package collections.dataAnalysis

import collections.dataAnalysis.AgePeriod.AgePeriod
import collections.dataAnalysis.Gender.Gender
import collections.dataAnalysis.WorkStatus.WorkStatus

case class Person(id: String, timePrimary: Int = 0, timeWorking: Int = 0, timeOther: Int = 0,
                  workStatus: WorkStatus = WorkStatus.Working, gender: Gender = Gender.Male,
                  agePeriod: AgePeriod = AgePeriod.Active)
