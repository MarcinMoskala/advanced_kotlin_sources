package f_03_property_delegation_4_map.s_10

fun main() {
    val mmap = mutableMapOf("a" to 10)
    val a by mmap
    mmap["a"] = 20
    println(a)
}
