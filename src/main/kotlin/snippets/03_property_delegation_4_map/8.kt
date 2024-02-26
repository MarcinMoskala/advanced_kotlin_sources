package f_03_property_delegation_4_map.s_8

fun main() {
    val list1 = mutableListOf(1, 2, 3)
    val list2 = list1
    list1 += 4
    println(list2)
}
