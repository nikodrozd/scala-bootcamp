package combatGame

class Player(val name: String, var hp: Int = 100, var weaponOpt: Option[Weapon] = None, var shieldOpt: Option[Shield] = None) {

  def attack (opponent: Player): Unit = {

    val damage: Option[Int] = this.weaponOpt.map(_.damage)
    val defence: Option[Int] = opponent.shieldOpt.map(_.defence)

    this.weaponOpt = durabilityCalculation(this.weaponOpt)
    opponent.shieldOpt = durabilityCalculation(opponent.shieldOpt)

    hpChangeCalculation(damage.getOrElse(0) - defence.getOrElse(0), opponent)
  }

  private def durabilityCalculation[A <: Item](itemOpt: Option[A]): Option[A] = {
    for {item <- itemOpt} yield  {
      item.durability -= 1
      if (item.durability <= 0) return None
    }
    itemOpt
  }

  private def hpChangeCalculation (hitPower: Int, opponent: Player): Unit = {
    if (hitPower > 0) {
      opponent.hp -= hitPower
      if (opponent.hp <= 0) winnerRoar(opponent.name)
    }
  }

  private[combatGame] def winnerRoar (name: String): Unit = {
    println(s"Yay, I beat $name")
  }

}
