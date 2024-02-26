package f_03_property_delegation_4_map.s_2

class User(val map: Map<String, Any>) {
    val id: Long by map
    val name: String by map
}

fun main() {
    val user = User(
        mapOf<String, Any>(
            "id" to 1234L,
            "name" to "Marcin"
        )
    )
    println(user.name) // Marcin
    println(user.id)  // 1234
    println(user.map)  // {id=1234, name=Marcin}
}
