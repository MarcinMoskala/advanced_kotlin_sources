```
val value by lazy { createValue() }

var items: List<Item> by
    Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

var key: String? by
    Delegates.observable(null) { _, old, new ->
        Log.e("key changed from $old to $new")
    }
```


```
// View and resource binding example in Android
private val button: Button by bindView(R.id.button)
private val textSize by bindDimension(R.dimen.font_size)
private val doctor: Doctor by argExtra(DOCTOR_ARG)

// Dependency Injection using Koin
private val presenter: MainPresenter by inject()
private val repository: NetworkRepository by inject()
private val viewModel: MainViewModel by viewModel()

// Data binding
private val port by bindConfiguration("port")
private val token: String by preferences.bind(TOKEN_KEY)
```


```
//1
var token: String? = null
    get() {
        println("token getter returned $field")
        return field
    }
    set(value) {
        println("token changed from $field to $value")
        field = value
    }

var attempts: Int = 0
    get() {
        println("attempts getter returned $field")
        return field
    }
    set(value) {
        println("attempts changed from $field to $value")
        field = value
    }

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
    attempts++
    // attempts getter returned 0
    // attempts changed from 0 to 1
}
```


```
//2
import kotlin.reflect.KProperty

private class LoggingProperty<T>(var value: T) {
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

var token: String? by LoggingProperty(null)
var attempts: Int by LoggingProperty(0)

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
    attempts++
    // attempts getter returned 0
    // attempts changed from 0 to 1
}
```


```
// Code in Kotlin:
var token: String? by LoggingProperty(null)

// What it is compiled to when a property is top-level
@JvmField
private val `token$delegate` = LoggingProperty<String?>(null)
var token: String?
    get() = `token$delegate`.getValue(null, ::token)
    set(value) {
        `token$delegate`.setValue(null, ::token, value)
    }

// What it is compiled to when a property is in a class
@JvmField
private val `token$delegate` = LoggingProperty<String?>(null)
var token: String?
    get() = `token$delegate`.getValue(this, this::token)
    set(value) {
        `token$delegate`.setValue(this, this::token, value)
    }
```


```
// Kotlin code:
var token: String? by LoggingProperty(null)

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
}
```


```
//3
import kotlin.reflect.KProperty

private class LoggingProperty<T>(
    var value: T
) {
    
    operator fun getValue(
        thisRef: Any?,
        prop: KProperty<*>
    ): T {
        println("${prop.name} in $thisRef getter returned $value")
        return value
    }
    
    operator fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        newValue: T
    ) {
        println("${prop.name} in $thisRef changed " + 
                "from $value to $newValue")
        value = newValue
    }
}

var token: String? by LoggingProperty(null)

object AttemptsCounter {
    var attempts: Int by LoggingProperty(0)
}

fun main() {
    token = "AAA" // token in null changed from null to AAA
    val res = token // token in null getter returned AAA
    println(res) // AAA
    
    AttemptsCounter.attempts = 1
    // attempts in AttemptsCounter@XYZ changed from 0 to 1
    val res2 = AttemptsCounter.attempts
    // attempts in AttemptsCounter@XYZ getter returned 1
    println(res2) // 1
}
```


```
class SwipeRefreshBinderDelegate(val id: Int) {
    private var cache: SwipeRefreshLayout? = null
    
    operator fun getValue(
        activity: Activity,
        prop: KProperty<*>
    ): SwipeRefreshLayout = cache ?: activity
        .findViewById<SwipeRefreshLayout>(id)
        .also { cache = it }
    
    operator fun getValue(
        fragment: Fragment,
        prop: KProperty<*>
    ): SwipeRefreshLayout = cache ?: fragment.view
        .findViewById<SwipeRefreshLayout>(id)
        .also { cache = it }
}
```


```
class EmptyPropertyDelegate {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): String = ""
    
    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: String
    ) {
        // no-op
    }
}

val p1: String by EmptyPropertyDelegate()
var p2: String by EmptyPropertyDelegate()
```


```
// Function from Kotlin stdlib
inline operator fun <V, V1 : V> Map<in String, V>.getValue(
    thisRef: Any?, 
    property: KProperty<*>
): V1 = getOrImplicitDefault(property.name) as V1

fun main() {
    val map: Map<String, Any> = mapOf(
        "name" to "Marcin",
        "kotlinProgrammer" to true
    )
    val name: String by map
    val kotlinProgrammer: Boolean by map
    print(name) // Marcin
    print(kotlinProgrammer) // true
    
    val incorrectName by map
    println(incorrectName) // Exception
}
```


```
fun interface ReadOnlyProperty<in T, out V> {
    operator fun getValue(
        thisRef: T,
        property: KProperty<*>
    ): V
}

interface ReadWriteProperty<in T, V>: ReadOnlyProperty<T,V> {
    override operator fun getValue(
        thisRef: T,
        property: KProperty<*>
    ): V
    
    operator fun setValue(
        thisRef: T,
        property: KProperty<*>,
        value: V
    )
}
```


```
private class LoggingProperty<T>(
    var value: T
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        println("${prop.name} getter returned $value")
        return value
    }
    
    override fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        newValue: T
    ) {
        println("${prop.name} changed from $value to $newValue")
        this.value = newValue
    }
}
```


```
//4
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
```


```
object UserPref : PreferenceHolder() {
    var splashScreenShown: Boolean by bindToPreference(true)
    var loginAttempts: Int by bindToPreference(0)
}
```


```
object UserPref : PreferenceHolder() {
    var splashScreenShown: Boolean by true
    var loginAttempts: Int by 0
}
```


```
abstract class PreferenceHolder {
    operator fun Boolean.provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ) = bindToPreference(this)
    
    operator fun Int.provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ) = bindToPreference(this)
    
    inline fun <reified T : Any> bindToPreference(
        default: T
    ): ReadWriteProperty<PreferenceHolder, T> = TODO()
}
```


```
class LoggingPropertyProvider<T>(
    private val value: T
) : PropertyDelegateProvider<Any?, LoggingProperty<T>> {
    
    override fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): LoggingProperty<T> = LoggingProperty(value)
}
```


```
//5
import kotlin.properties.Delegates

var a: Int by Delegates.notNull()
var b: String by Delegates.notNull()

fun main() {
    a = 10
    println(a) // 10
    a = 20
    println(a) // 20
    
    println(b) // IllegalStateException:
    // Property b should be initialized before getting.
}
```


```
lateinit var i: Int // Compilation Error
lateinit var b: Boolean // Compilation Error
```


```
var i: Int by Delegates.notNull()
var b: Boolean by Delegates.notNull()
```


```
abstract class IntegrationTest {
    
    @Value("${server.port}")
    var serverPort: Int by Delegates.notNull()

    // ...
}

// DSL builder
fun person(block: PersonBuilder.() -> Unit): Person =
    PersonBuilder().apply(block).build()

class PersonBuilder() {
    lateinit var name: String
    var age: Int by Delegates.notNull()
    fun build(): Person = Person(name, age)
}

// DSL use
val person = person {
    name = "Marc"
    age = 30
}
```