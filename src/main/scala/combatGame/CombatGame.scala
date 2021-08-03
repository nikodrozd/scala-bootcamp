package combatGame

object CombatGame {
  def main(args: Array[String]): Unit = {
    val player1: Player = new Player(name = "John", shieldOpt = Some(BucklerShield()))
    val player2: Player = new Player(name = "Jack", weaponOpt = Some(Dagger()), shieldOpt = Some(BucklerShield()))

    println(s"HP = ${player1.hp}, Shield = ${player1.shieldOpt}")
    player2.attack(player1)
    println(s"HP = ${player1.hp}, Shield = ${player1.shieldOpt}")
    player2.attack(player1)
    println(s"HP = ${player1.hp}, Shield = ${player1.shieldOpt}")
  }
}
