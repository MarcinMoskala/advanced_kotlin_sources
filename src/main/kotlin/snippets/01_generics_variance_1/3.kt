package f_01_generics_variance_1.s_3

fun printProcessedNumber(transformation: (Int) -> Any) {
    println(transformation(42))
}
