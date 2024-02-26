package f_02_interface_delegation.s_5

interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin(
    private val creature: Creature = GenericCreature(2, 1)
) : Creature by creature {
    override fun attack() {
        println("It will be special Goblin attack!")
        creature.attack()
    }
}

fun main() {
    val goblin = Goblin()
    goblin.attack()
    // It will be a special Goblin attack!
    // Attacking with 2
}
