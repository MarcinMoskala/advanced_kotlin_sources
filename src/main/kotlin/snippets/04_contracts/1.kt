package f_04_contracts.s_1

fun mul(x: Int, y: Int): Int {
    require(x > 0)
    require(y > 0)
    return x * y
}
