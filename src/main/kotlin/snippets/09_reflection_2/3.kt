package f_09_reflection_2.s_3

package a.b.c

class D {
    class E
}

fun main() {
    val clazz = D.E::class
    println(clazz.simpleName) // E
    println(clazz.qualifiedName) // a.b.c.D.E
}
