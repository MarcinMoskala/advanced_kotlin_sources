package f_03_property_delegation_1.s_1

var token: String? = null
    get() {
        println("token getter returned $field")
        return field
    }
    set(value) {
        println("token changed from $field to $value")
        field = value
    }

var attempts: Int = 0
    get() {
        println("attempts getter returned $field")
        return field
    }
    set(value) {
        println("attempts changed from $field to $value")
        field = value
    }

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
    attempts++
    // attempts getter returned 0
    // attempts changed from 0 to 1
}
