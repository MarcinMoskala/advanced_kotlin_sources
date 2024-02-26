package f_02_interface_delegation.s_4

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

class Goblin : Creature by GenericCreature(2, 1) {
    override fun attack() {
        println("Special Goblin attack $attackPower")
    }
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Special Goblin attack 2
}
