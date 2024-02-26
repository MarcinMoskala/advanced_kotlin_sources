package f_09_reflection_3.s_2

import kotlin.reflect.typeOf

fun main() {
    println(typeOf<Int>().isMarkedNullable) // false
    println(typeOf<Int?>().isMarkedNullable) // true
}
