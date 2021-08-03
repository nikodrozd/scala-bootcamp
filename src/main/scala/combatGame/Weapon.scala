package combatGame

trait Weapon {
  val damage: Int
  var durability: Int
}

case class Sword() extends Weapon {
  override val damage: Int = 10
  override var durability: Int = 10
}

case class Rapier() extends Weapon {
  override val damage: Int = 5
  override var durability: Int = 5
}

case class Dagger() extends Weapon {
  override val damage: Int = 3
  override var durability: Int = 3
}