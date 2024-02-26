package f_05_java_interop_2.s_4

annotation class A
annotation class B

@A
class User @B constructor(
    val name: String
)
