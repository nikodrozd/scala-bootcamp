package crossRoad

object CrossRoad {

  def main(args: Array[String]): Unit = {
    val adult: Person = Adult("John", 33)
    val teen: Person = Teenager("Tommy", 15)

    println("Adult for enum:")
    adult.crossRoadEnum(TrafficLightEnumerator.Green)
    adult.crossRoadEnum(TrafficLightEnumerator.Yellow)
    adult.crossRoadEnum(TrafficLightEnumerator.Red)
    println()

    println("Adult for trait:")
    adult.crossRoadTrait(Green)
    adult.crossRoadTrait(Yellow)
    adult.crossRoadTrait(Red)
    println()

    println("Teen for enum:")
    teen.crossRoadEnum(TrafficLightEnumerator.Green)
    teen.crossRoadEnum(TrafficLightEnumerator.Yellow)
    teen.crossRoadEnum(TrafficLightEnumerator.Red)
    println()

    println("Teen for trait:")
    teen.crossRoadTrait(Green)
    teen.crossRoadTrait(Yellow)
    teen.crossRoadTrait(Red)
    println()

  }

}
