package f_09_reflection_2.s_11

class A {
    class B
    inner class C
}

fun main() {
    println(A::class.nestedClasses) // [class A$B, class A$C]
}
