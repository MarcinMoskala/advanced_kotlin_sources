package playground

import kotlin.reflect.KFunction

class User(val name: String) {
    constructor(user: User) : this(user.name)
    constructor(json: UserJson) : this(json.name)
}

class UserJson(val name: String)

fun main() {
    val constructors: Collection<KFunction<User>> =
        User::class.constructors
    
    println(constructors.size) // 3
    constructors.forEach(::println)
    // fun <init>(playground.User): playground.User
    // fun <init>(playground.UserJson): playground.User
    // fun <init>(kotlin.String): playground.User
}
