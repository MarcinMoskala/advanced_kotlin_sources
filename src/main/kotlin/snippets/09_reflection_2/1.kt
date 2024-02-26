package f_09_reflection_2.s_1

import kotlin.reflect.KClass

class A

fun main() {
    val class1: KClass<A> = A::class
    println(class1) // class A
    
    val a: A = A()
    val class2: KClass<out A> = a::class
    println(class2) // class A
}
