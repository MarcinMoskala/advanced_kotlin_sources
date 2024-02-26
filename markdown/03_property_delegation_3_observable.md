```
var name: String by
    Delegates.observable("Empty") { prop, old, new ->
        println("$old -> $new")
    }

fun main() {
    name = "Martin" // Empty -> Martin
    name = "Igor" // Martin -> Igor
    name = "Igor" // Igor -> Igor
}
```


```
var prop: SomeType by Delegates.observable(initial, operation)

// Alternative to
var prop: SomeType = initial
    set(newValue) {
        field = newValue
        operation(::prop, field, newValue)
    }
```


```
import kotlin.properties.Delegates.observable

val prop: SomeType by observable(initial, operation)
```


```
val status by observable(INITIAL) { _, old, new ->
    log.info("Status changed from $old to $new")
}
```


```
var elements by observable(elements) { _, old, new ->
    if (new != old) notifyDataSetChanged()
}
```


```
//1
import kotlin.properties.Delegates.observable

class ObservableProperty<T>(initial: T) {
    private var observers: List<(T) -> Unit> = listOf()
    
    var value: T by observable(initial) { _, _, new ->
        observers.forEach { it(new) }
    }
    
    fun addObserver(observer: (T) -> Unit) {
        observers += observer
    }
}

fun main() {
    val name = ObservableProperty("")
    name.addObserver { println("Changed to $it") }
    name.value = "A"
    // Changed to A
    name.addObserver { println("Now it is $it") }
    name.value = "B"
    // Changed to B
    // Now it is B
}
```


```
var drawerOpen by observable(false) { _, _, open ->
    if (open) drawerLayout.openDrawer(GravityCompat.START)
    else drawerLayout.closeDrawer(GravityCompat.START)
}
```


```
var drawerOpen by bindToDrawerOpen(false) { drawerLayout }

fun bindToDrawerOpen(
    initial: Boolean,
    lazyView: () -> DrawerLayout
) = observable(initial) { _, _, open ->
    if (open) drawerLayout.openDrawer(GravityCompat.START)
    else drawerLayout.closeDrawer(GravityCompat.START)
}
```


```
//2
import kotlin.properties.Delegates.observable

var book: String by observable("") { _, _, _ ->
    page = 0
}
var page = 0

fun main() {
    book = "TheWitcher"
    repeat(69) { page++ }
    println(book) // TheWitcher
    println(page) // 69
    book = "Ice"
    println(book) // Ice
    println(page) // 0
}
```


```
var presenters: List<Presenter> by
    observable(emptyList()) { _, old, new ->
        (new - old).forEach { it.onCreate() }
        (old - new).forEach { it.onDestroy() }
    }
```


```
var prop: SomeType by Delegates.vetoable(initial, operation)

// Alternative to
var prop: SomeType = initial
    set(newValue) {
        if (operation(::prop, field, newValue)) {
            field = newValue
        }
    }
```


```
//3
import kotlin.properties.Delegates.vetoable

var smallList: List<String> by
    vetoable(emptyList()) { _, _, new ->
        println(new)
        new.size <= 3
    }

fun main() {
    smallList = listOf("A", "B", "C") // [A, B, C]
    println(smallList) // [A, B, C]
    smallList = listOf("D", "E", "F", "G") // [D, E, F, G]
    println(smallList) // [A, B, C]
    smallList = listOf("H") // [H]
    println(smallList) // [H]
}
```


```
var name: String by vetoable("") { _, _, new ->
    if (isValid(new)) {
        showNewName(new)
        true
    } else {
        showNameError()
        false
    }
}
```


```
import kotlin.properties.Delegates.vetoable

val state by vetoable(Initial) { _, _, newState ->
    if (newState is Initial) {
        log.e("Cannot set initial state")
        return@vetoable false
    }
    // ...
    return@vetoable true
}

val email by vetoable(email) { _, _, newEmail ->
    emailRegex.matches(newEmail)
}
```