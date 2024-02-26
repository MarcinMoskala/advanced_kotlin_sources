package f_03_property_delegation_3_observable.s_2

import kotlin.properties.Delegates.observable

var book: String by observable("") { _, _, _ ->
    page = 0
}
var page = 0

fun main() {
    book = "TheWitcher"
    repeat(69) { page++ }
    println(book) // TheWitcher
    println(page) // 69
    book = "Ice"
    println(book) // Ice
    println(page) // 0
}
