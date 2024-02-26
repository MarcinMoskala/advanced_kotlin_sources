package f_09_reflection_3.s_1

import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun main() {
    val t1: KType = typeOf<Int?>()
    println(t1) // kotlin.Int?
    val t2: KType = typeOf<List<Int?>>()
    println(t2) // kotlin.collections.List<kotlin.Int?>
    val t3: KType = typeOf<() -> Map<Int, Char?>>()
    println(t3)
    // () -> kotlin.collections.Map<kotlin.Int, kotlin.Char?>
}
