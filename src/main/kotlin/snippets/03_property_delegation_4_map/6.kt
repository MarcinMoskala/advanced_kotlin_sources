package f_03_property_delegation_4_map.s_6

fun main() {
    val user1 = object {
        var name: String = "Rafał"
    }
    val user2 = user1
    user1.name = "Bartek"
    println(user2.name)
}
