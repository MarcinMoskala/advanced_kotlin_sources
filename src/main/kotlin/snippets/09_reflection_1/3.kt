package f_09_reflection_1.s_3

import kotlin.reflect.KCallable

fun add(i: Int, j: Int) = i + j

fun main() {
    val f: KCallable<Int> = ::add
    println(f.call(1, 2)) // 3
    println(f.call("A", "B")) // IllegalArgumentException
}
