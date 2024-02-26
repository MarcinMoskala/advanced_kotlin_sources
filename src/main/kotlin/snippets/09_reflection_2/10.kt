package f_09_reflection_2.s_10

fun main() {
    println(List::class.typeParameters) // [out E]
    println(Map::class.typeParameters) // [K, out V]
}
