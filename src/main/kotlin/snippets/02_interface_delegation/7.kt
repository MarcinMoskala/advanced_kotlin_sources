package f_02_interface_delegation.s_7

interface Attack {
    val attack: Int
    val defense: Int
}
interface Defense {
    val defense: Int
}
class Dagger : Attack {
    override val attack: Int = 1
    override val defense: Int = -1
}
class LeatherArmour : Defense {
    override val defense: Int = 2
}
class Goblin(
    private val attackDelegate: Attack = Dagger(),
    private val defenseDelegate: Defense = LeatherArmour(),
) : Attack by attackDelegate, Defense by defenseDelegate {
    // We must override this property
    override val defense: Int =
        defenseDelegate.defense + attackDelegate.defense
}
