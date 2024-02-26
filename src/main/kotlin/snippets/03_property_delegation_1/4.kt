package f_03_property_delegation_1.s_4

import kotlin.reflect.KProperty

class LoggingProperty<T>(var value: T) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        println("${prop.name} getter returned $value")
        return value
    }
    operator fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        newValue: T
    ) {
        println("${prop.name} changed from $value to $newValue")
        value = newValue
    }
}

class LoggingPropertyProvider<T>(
    private val value: T
) {
    operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): LoggingProperty<T> = LoggingProperty(value)
}

var token: String? by LoggingPropertyProvider(null)
var attempts: Int by LoggingPropertyProvider(0)

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
}
