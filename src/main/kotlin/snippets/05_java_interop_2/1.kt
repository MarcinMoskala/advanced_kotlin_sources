package f_05_java_interop_2.s_1

import kotlin.properties.Delegates.notNull

class User {
    var name = "ABC" // getter, setter, field
    var surname: String by notNull() //getter, setter, delegate
    val fullName: String // only getter
        get() = "$name $surname"
}
