package crossRoad

import crossRoad.TrafficLightEnumerator.TrafficLightEnumerator

trait Person {

  val name: String
  val age: Int
  val stringToStop: String = "Stop"
  val stringToCrossTheRoad: String = "Cross the road"
  val stringForActionOnPreparation: String

  def crossRoadEnum(color: TrafficLightEnumerator): Unit = {
    color match {
      case TrafficLightEnumerator.Green => println(stringToCrossTheRoad)
      case TrafficLightEnumerator.Yellow => println(stringForActionOnPreparation)
      case TrafficLightEnumerator.Red => println(stringToStop)
    }
  }

  def crossRoadTrait(color: TrafficLight): Unit = {
    color match {
      case Green => println(stringToCrossTheRoad)
      case Yellow => println(stringForActionOnPreparation)
      case Red => println(stringToStop)
    }
  }

}

case class Adult(name: String, age: Int) extends Person {

  override val stringForActionOnPreparation: String = "Stop walking"

}
case class Teenager(name: String, age: Int) extends Person {

  override val stringForActionOnPreparation: String = "Start running"

}

