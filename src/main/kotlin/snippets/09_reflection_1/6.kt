package f_09_reflection_1.s_6

import kotlin.reflect.*

class Box(
    var value: Int = 0
)

fun main() {
    val box = Box()
    val p: KMutableProperty1<Box, Int> = Box::value
    println(p.get(box)) // 0
    p.set(box, 999)
    println(p.get(box)) // 999
}
