package f_01_generics_variance_3.s_4

class Box<in T>(
    private val value: T
) {
    
    private fun get(): T = value
        ?: error("Value not set")
}
