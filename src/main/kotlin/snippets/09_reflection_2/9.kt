package f_09_reflection_2.s_9

interface I1
interface I2
open class A : I1
class B : A(), I2

fun main() {
    val a = A()
    val b = B()
    println(A::class.isInstance(a)) // true
    println(B::class.isInstance(a)) // false
    println(I1::class.isInstance(a)) // true
    println(I2::class.isInstance(a)) // false
    
    println(A::class.isInstance(b)) // true
    println(B::class.isInstance(b)) // true
    println(I1::class.isInstance(b)) // true
    println(I2::class.isInstance(b)) // true
}
