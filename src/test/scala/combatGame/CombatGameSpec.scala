package combatGame

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CombatGameSpec extends AnyFlatSpec with Matchers with GivenWhenThen {

  "Attack player" should "hit 5 hp damage using Sword vs LightShield" in {
    Given("two players: attacker with Sword and defender with LightShield")
    val attacker: Player = new Player("Attacker", weaponOpt = Some(Sword()))
    val defender: Player = new Player("Defender", shieldOpt = Some(LightShield()))

    When("attacker attacks defender")
    attacker.attack(defender)

    Then("defender should have 95 hp out of 100")
    defender.hp should equal(95)
  }

  it should "damage 0 hp in case if shield defence >= weapon attack" in {
    Given("two players: attacker with Dagger and defender with LightShield")
    val attacker: Player = new Player("Attacker", weaponOpt = Some(Dagger()))
    val defender: Player = new Player("Defender", shieldOpt = Some(LightShield()))

    When("attacker attacks defender")
    attacker.attack(defender)

    Then("defender still should have 100 hp")
    defender.hp should equal(100)
    Then("shield durability should be decreased from 3 to 2")
    defender.shieldOpt.map(shield => shield.durability).getOrElse(0) should equal(2)
    Then("weapon durability should be decreased from 3 to 2")
    attacker.weaponOpt.map(weapon => weapon.durability).getOrElse(0) should equal(2)
  }

  it should "break defender's shield if it's durability will be 0" in {
    Given("two players: attacker with Sword and defender with BucklerShield")
    val attacker: Player = new Player("Attacker", weaponOpt = Some(Sword()))
    val defender: Player = new Player("Defender", shieldOpt = Some(BucklerShield()))

    When("attacker attacks defender 1 time to destroy BucklerShield")
    attacker.attack(defender)

    Then("defender's shield should be broken")
    defender.shieldOpt should equal(None)
  }

  it should "have broken weapon after it's durability reach 0 points" in {
    Given("two players: attacker with Dagger and defender with HeavyShield")
    val attacker: Player = new Player("Attacker", weaponOpt = Some(Dagger()))
    val defender: Player = new Player("Defender", shieldOpt = Some(HeavyShield()))

    When("attacker attacks defender 3 times to destroy Dagger")
    attacker.attack(defender)
    attacker.attack(defender)
    attacker.attack(defender)

    Then("attacker's weapon should be broken")
    attacker.weaponOpt should equal(None)
  }

}
