package f_09_reflection_3.s_7

import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

class A {
    private var value = 0
    private fun printValue() {
        println(value)
    }
    override fun toString(): String =
        "A(value=$value)"
}

fun main() {
    val a = A()
    val c = A::class
    
    // We change value to 999
    val prop = c.declaredMemberProperties
        .find { it.name == "value" } as? KMutableProperty1<A, Int>
    prop?.isAccessible = true
    prop?.set(a, 999)
    println(a) // A(value=999)
    println(prop?.get(a)) // 999
    
    // We call printValue function
    val func: KFunction<*>? = c.declaredMemberFunctions
        .find { it.name == "printValue" }
    func?.isAccessible = true
    func?.call(a) // 999
}
