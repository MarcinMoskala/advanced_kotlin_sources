package f_03_property_delegation_3_observable.s_3

import kotlin.properties.Delegates.vetoable

var smallList: List<String> by
    vetoable(emptyList()) { _, _, new ->
        println(new)
        new.size <= 3
    }

fun main() {
    smallList = listOf("A", "B", "C") // [A, B, C]
    println(smallList) // [A, B, C]
    smallList = listOf("D", "E", "F", "G") // [D, E, F, G]
    println(smallList) // [A, B, C]
    smallList = listOf("H") // [H]
    println(smallList) // [H]
}
