package f_03_property_delegation_4_map.s_1

fun main() {
    val map: Map<String, Any> = mapOf(
        "name" to "Marcin",
        "kotlinProgrammer" to true
    )
    val name: String by map
    val kotlinProgrammer: Boolean by map
    println(name) // Marcin
    println(kotlinProgrammer) // true
}
