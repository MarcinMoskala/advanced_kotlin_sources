package f_03_property_delegation_4_map.s_4

class Population(var cities: Map<String, Int>) {
    val sanFrancisco by cities
    val tallinn by cities
    val kotlin by cities
}

val population = Population(
    mapOf(
        "sanFrancisco" to 864_816,
        "tallinn" to 413_782,
        "kotlin" to 43_005
    )
)

fun main(args: Array<String>) {
    // Years has passed,
    // now we all live on Mars
    population.cities = emptyMap()
    println(population.sanFrancisco)
    println(population.tallinn)
    println(population.kotlin)
}
