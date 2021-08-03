package combatGame

class Player(val name: String, var hp: Int = 100, var weaponOpt: Option[Weapon] = None, var shieldOpt: Option[Shield] = None) {

  def attack (opponent: Player): Unit = {

    //calculation of weapon and shield parameters
    val damage: Option[Int] = for {weapon <- this.weaponOpt} yield weapon.damage
    val defence: Option[Int] = for {shield <- opponent.shieldOpt} yield shield.defence

    //calculation of weapon and shield durability
    for {weapon <- this.weaponOpt} yield  {
      weapon.durability -= 1
      if (weapon.durability <= 0) this.weaponOpt = None
    }
    for {shield <- opponent.shieldOpt} yield {
      shield.durability -= 1
      if (shield.durability <= 0) opponent.shieldOpt = None
    }

    //calculation of opponent's hp change
    val hitPower = damage.getOrElse(0) - defence.getOrElse(0)

    if (hitPower > 0) {
      opponent.hp -= hitPower
      if (opponent.hp <= 0) println(s"Yay, I beat ${opponent.name}")
    }

  }
}
