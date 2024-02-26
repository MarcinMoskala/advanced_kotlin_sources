package f_02_interface_delegation.s_6

interface Creature {
    fun attack()
}

class GenericCreature : Creature {
    override fun attack() {
        // this.javaClass.name is always GenericCreature
        println("${this::class.simpleName} attacks")
    }
    
    fun walk() {}
}

class Goblin : Creature by GenericCreature()

open class WildAnimal : Creature {
    override fun attack() {
        // this.javaClass.name depends on actual class
        println("${this::class.simpleName} attacks")
    }
}

class Wolf : WildAnimal()

fun main() {
    GenericCreature().attack() // GenericCreature attacks
    Goblin().attack() // GenericCreature attacks
    WildAnimal().attack() // WildAnimal attacks
    Wolf().attack() // Wolf attacks
    // Goblin().walk() COMPILATION ERROR, no such method
}
