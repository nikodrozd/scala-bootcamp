package crossRoad

import crossRoad.TrafficLightEnumerator.TrafficLightEnumerator

trait Person {
  val name: String
  val age: Int

  def crossRoadEnum(color: TrafficLightEnumerator)
  def crossRoadTrait(color: TrafficLight)
}

case class Adult(name: String, age: Int) extends Person {
  override def crossRoadEnum(color: TrafficLightEnumerator): Unit = {
    color match {
      case TrafficLightEnumerator.Green => println("Cross the road")
      case TrafficLightEnumerator.Yellow => println("Stop walking")
      case TrafficLightEnumerator.Red => println("Stop")
    }
  }

  override def crossRoadTrait(color: TrafficLight): Unit = {
    color match {
      case Green => println("Cross the road")
      case Yellow => println("Stop walking")
      case Red => println("Stop")
    }
  }
}
case class Teenager(name: String, age: Int) extends Person {
  override def crossRoadEnum(color: TrafficLightEnumerator): Unit = {
    color match {
      case TrafficLightEnumerator.Green => println("Cross the road")
      case TrafficLightEnumerator.Yellow => println("Start running")
      case TrafficLightEnumerator.Red => println("Stop")
    }
  }

  override def crossRoadTrait(color: TrafficLight): Unit = {
    color match {
      case Green => println("Cross the road")
      case Yellow => println("Start running")
      case Red => println("Stop")
    }
  }

}

