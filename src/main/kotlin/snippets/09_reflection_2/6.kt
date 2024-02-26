package f_09_reflection_2.s_6

import kotlin.reflect.full.*

open class Parent {
    val a = 12
    fun b() {}
}

class Child : Parent() {
    val c = 12
    fun d() {}
}

fun Child.e() {}

fun main() {
    println(Child::class.members.map { it.name })
    // [c, d, a, b, equals, hashCode, toString]
    println(Child::class.functions.map { it.name })
    // [d, b, equals, hashCode, toString]
    println(Child::class.memberProperties.map { it.name })
    // [c, a]

    println(Child::class.declaredMembers.map { it.name })
    // [c, d]
    println(Child::class.declaredFunctions.map { it.name }) 
    // [d]
    println(Child::class.declaredMemberProperties.map { it.name })
    // [c]
}
