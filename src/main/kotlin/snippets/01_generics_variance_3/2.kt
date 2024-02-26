package f_01_generics_variance_3.s_2

class Box<out T> {
    private var value: T? = null
    
    private fun set(value: T) { // OK
        this.value = value
    }
    
    fun get(): T = value ?: error("Value not set")
}
