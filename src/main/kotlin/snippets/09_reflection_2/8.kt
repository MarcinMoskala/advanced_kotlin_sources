package f_09_reflection_2.s_8

import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

interface I1
interface I2
open class A : I1
class B : A(), I2

fun main() {
    val a = A::class
    val b = B::class
    println(a.superclasses) // [class I1, class kotlin.Any]
    println(b.superclasses) // [class A, class I2]
    println(a.supertypes) // [I1, kotlin.Any]
    println(b.supertypes) // [A, I2]
}
