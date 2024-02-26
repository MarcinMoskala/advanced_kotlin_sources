package f_01_generics_variance_3.s_7

interface Dog
interface Pet
data class Puppy(val name: String) : Dog, Pet
data class Wolf(val name: String) : Dog
data class Cat(val name: String) : Pet

fun fillWithPuppies(list: MutableList<in Puppy>) {
    list.add(Puppy("Jim"))
    list.add(Puppy("Beam"))
}

fun main() {
    val dogs = mutableListOf<Dog>(Wolf("Pluto"))
    fillWithPuppies(dogs)
    println(dogs)
    // [Wolf(name=Pluto), Puppy(name=Jim), Puppy(name=Beam)]
    
    val pets = mutableListOf<Pet>(Cat("Felix"))
    fillWithPuppies(pets)
    println(pets)
    // [Cat(name=Felix), Puppy(name=Jim), Puppy(name=Beam)]
}
