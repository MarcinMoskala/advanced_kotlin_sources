package f_09_reflection_3.s_6

import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

class A {
    val a = 123
    fun b() {}
}

fun main() {
    val c1: Class<A> = A::class.java
    val c2: Class<A> = A().javaClass
    
    val f1: Field? = A::a.javaField
    val f2: Method? = A::a.javaGetter
    val f3: Method? = A::b.javaMethod
    
    val kotlinClass: KClass<A> = c1.kotlin
    val kotlinProperty: KProperty<*>? = f1?.kotlinProperty
    val kotlinFunction: KFunction<*>? = f3?.kotlinFunction
}
