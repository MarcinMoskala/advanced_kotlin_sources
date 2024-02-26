package f_03_property_delegation_4_map.s_9

fun main() {
    var map = mapOf("a" to 10)
    val a by map
    map = mapOf("a" to 20)
    println(a)
}
