package f_05_java_interop_3.s_1

@JvmName("averageLongList")
fun List<Long>.average() = sum().toDouble() / size

@JvmName("averageIntList")
fun List<Int>.average() = sum().toDouble() / size

fun main() {
    val ints: List<Int> = List(10) { it }
    println(ints.average()) // 4.5
    val longs: List<Long> = List(10) { it.toLong() }
    println(longs.average()) // 4.5
}
