package crossRoad

object CrossRoad {

  def main(args: Array[String]): Unit = {
    val adult: Person = Adult("John", 33)
    val teen: Person = Teenager("Tommy", 15)

    println("Adult for enum:")
    TrafficLightEnumerator.values.foreach(x => adult.crossRoadEnum(x))
    println()

    println("Adult for trait:")
    adult.crossRoadTrait(Red)
    adult.crossRoadTrait(Yellow)
    adult.crossRoadTrait(Green)
    println()

    println("Teen for enum:")
    TrafficLightEnumerator.values.foreach(x => teen.crossRoadEnum(x))
    println()

    println("Teen for trait:")
    teen.crossRoadTrait(Red)
    teen.crossRoadTrait(Yellow)
    teen.crossRoadTrait(Green)
    println()

  }

}
