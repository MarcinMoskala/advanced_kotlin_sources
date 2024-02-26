package f_04_contracts.s_4

fun main() {
    run {
        println("A")
        return
        println("B") // unreachable
    }
    println("C") // unreachable
}
