package f_03_property_delegation_4_map.s_3

class User(val map: MutableMap<String, Any>) {
    var id: Long by map
    var name: String by map
}

fun main() {
    val user = User(
        mutableMapOf(
            "id" to 123L,
            "name" to "Alek",
        )
    )

    println(user.name)  // Alek
    println(user.id)  // 123

    user.name = "Bolek"
    println(user.name)  // Bolek
    println(user.map)  // {id=123, name=Bolek}
    
    user.map["id"] = 456
    println(user.id)  // 456
    println(user.map)  // {id=456, name=Bolek}
}
