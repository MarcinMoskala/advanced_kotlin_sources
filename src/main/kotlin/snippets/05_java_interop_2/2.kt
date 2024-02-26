package f_05_java_interop_2.s_2

annotation class A
annotation class B
annotation class C
annotation class D
annotation class E

class User {
    @property:A
    @get:B
    @set:C
    @field:D
    @setparam:E
    var name = "ABC"
}
