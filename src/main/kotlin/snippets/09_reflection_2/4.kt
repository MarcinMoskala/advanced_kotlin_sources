package f_09_reflection_2.s_4

fun main() {
    val o = object {}
    val clazz = o::class
    println(clazz.simpleName) // null
    println(clazz.qualifiedName) // null
}
