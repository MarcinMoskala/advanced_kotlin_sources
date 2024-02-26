package f_09_reflection_3.s_3

import kotlin.reflect.typeOf

class Box<T>

fun main() {
    val t1 = typeOf<List<Int>>()
    println(t1.arguments) // [kotlin.Int]
    val t2 = typeOf<Map<Long, Char>>()
    println(t2.arguments) // [kotlin.Long, kotlin.Char]
    val t3 = typeOf<Box<out String>>()
    println(t3.arguments) // [out kotlin.String]
}
