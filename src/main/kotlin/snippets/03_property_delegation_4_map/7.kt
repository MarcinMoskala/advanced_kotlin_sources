package f_03_property_delegation_4_map.s_7

fun main() {
    var list1 = listOf(1, 2, 3)
    var list2 = list1
    list1 += 4
    println(list2)
}
