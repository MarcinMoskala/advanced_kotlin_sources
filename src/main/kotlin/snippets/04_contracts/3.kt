package f_04_contracts.s_3

fun main() {
    run {
        println("A")
        return
        println("B") // unreachable
    }
    println("C") // unreachable
}
