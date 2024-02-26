package f_02_interface_delegation.s_2

interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

open class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin : GenericCreature(2, 1) {
    // ...
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Attacking with 2
}
