package combatGame

class Player(val name: String, var hp: Int = 100, var weaponOpt: Option[Weapon] = None, var shieldOpt: Option[Shield] = None) {

  def attack (opponent: Player): Unit = {

    val damage: Option[Int] = this.weaponOpt.map(_.damage)
    val defence: Option[Int] = opponent.shieldOpt.map(_.defence)

    durabilityCalculation(this, opponent)
    hpChangeCalculation(damage.getOrElse(0) - defence.getOrElse(0), opponent)
  }

  private def durabilityCalculation(attacker: Player, defender: Player) {
    for {weapon <- attacker.weaponOpt} yield  {
      weapon.durability -= 1
      if (weapon.durability <= 0) attacker.weaponOpt = None
    }
    for {shield <- defender.shieldOpt} yield {
      shield.durability -= 1
      if (shield.durability <= 0) defender.shieldOpt = None
    }
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
