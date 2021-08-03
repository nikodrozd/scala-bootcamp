package combatGame

trait Shield {
  val defence: Int
  var durability: Int
}

case class BucklerShield() extends Shield {
  override val defence: Int = 3
  override var durability: Int = 1
}

case class LightShield() extends Shield {
  override val defence: Int = 5
  override var durability: Int = 3
}

case class HeavyShield() extends Shield {
  override val defence: Int = 10
  override var durability: Int = 5
}