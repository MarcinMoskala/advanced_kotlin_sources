package f_03_property_delegation_1.s_5

import kotlin.properties.Delegates

var a: Int by Delegates.notNull()
var b: String by Delegates.notNull()

fun main() {
    a = 10
    println(a) // 10
    a = 20
    println(a) // 20
    
    println(b) // IllegalStateException:
    // Property b should be initialized before getting.
}
