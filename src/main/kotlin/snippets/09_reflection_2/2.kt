package f_09_reflection_2.s_2

import kotlin.reflect.KClass

open class A
class B : A()

fun main() {
    val a: A = B()
    val clazz: KClass<out A> = a::class
    println(clazz) // class B
}
