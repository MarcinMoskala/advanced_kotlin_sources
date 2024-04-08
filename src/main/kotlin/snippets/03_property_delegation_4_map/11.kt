package f_03_property_delegation_4_map.s_11

var map = mapOf("a" to 10)
val `a$delegate` = map
val a: Int
    get() = `a$delegate`.getValue(null, ::a)

val mmap = mutableMapOf("b" to 10)
val `b$delegate` = mmap
val b: Int
    get() = `b$delegate`.getValue(null, ::b)

fun main() {
    map = mapOf("a" to 20)
    println(a) // 10

    mmap["b"] = 20
    println(b) // 20
}
