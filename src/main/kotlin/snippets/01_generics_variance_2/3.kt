package f_01_generics_variance_2.s_3

fun main() {
    val empty: List<Nothing> = emptyList()
    val strs: List<String> = empty
    val ints: List<Int> = empty

    val other: List<Char> = emptyList()
    println(empty === other) // true
}
