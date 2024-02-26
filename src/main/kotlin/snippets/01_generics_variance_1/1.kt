package f_01_generics_variance_1.s_1

interface Animal {
    fun pet()
}

class Cat(val name: String) : Animal {
    override fun pet() {
        println("$name says Meow")
    }
}

fun petAnimals(animals: List<Animal>) {
    for (animal in animals) {
        animal.pet()
    }
}

fun main() {
    val cats: List<Cat> = listOf(Cat("Mruczek"), Cat("Puszek"))
    petAnimals(cats) // Can I do that?
}
