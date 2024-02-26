```
//1
fun main() {
    val map: Map<String, Any> = mapOf(
        "name" to "Marcin",
        "kotlinProgrammer" to true
    )
    val name: String by map
    val kotlinProgrammer: Boolean by map
    println(name) // Marcin
    println(kotlinProgrammer) // true
}
```


```
operator fun <V, V1 : V> Map<String, V>.getValue(
    thisRef: Any?,
    property: KProperty<*>
): V1 {
    val key = property.name
    val value = get(key)
    if (value == null && !containsKey(key)) {
        throw NoSuchElementException(
            "Key ${property.name} is missing in the map."
        )
    } else {
        return value as V1
    }
}
```


```
//2
class User(val map: Map<String, Any>) {
    val id: Long by map
    val name: String by map
}

fun main() {
    val user = User(
        mapOf<String, Any>(
            "id" to 1234L,
            "name" to "Marcin"
        )
    )
    println(user.name) // Marcin
    println(user.id)  // 1234
    println(user.map)  // {id=1234, name=Marcin}
}
```


```
//3
class User(val map: MutableMap<String, Any>) {
    var id: Long by map
    var name: String by map
}

fun main() {
    val user = User(
        mutableMapOf(
            "id" to 123L,
            "name" to "Alek",
        )
    )

    println(user.name)  // Alek
    println(user.id)  // 123

    user.name = "Bolek"
    println(user.name)  // Bolek
    println(user.map)  // {id=123, name=Bolek}
    
    user.map["id"] = 456
    println(user.id)  // 456
    println(user.map)  // {id=456, name=Bolek}
}
```


```
//4
class Population(var cities: Map<String, Int>) {
    val sanFrancisco by cities
    val tallinn by cities
    val kotlin by cities
}

val population = Population(
    mapOf(
        "sanFrancisco" to 864_816,
        "tallinn" to 413_782,
        "kotlin" to 43_005
    )
)

fun main(args: Array<String>) {
    // Years has passed,
    // now we all live on Mars
    population.cities = emptyMap()
    println(population.sanFrancisco)
    println(population.tallinn)
    println(population.kotlin)
}
```


```
//5
fun main() {
    var a = 10
    var b = a
    a = 20
    println(b)
}
```


```
//6
fun main() {
    val user1 = object {
        var name: String = "Rafał"
    }
    val user2 = user1
    user1.name = "Bartek"
    println(user2.name)
}
```


```
interface Nameable {
    val name: String
}

fun main() {
    var user1: Namable = object : Nameable {
        override var name: String = "Rafał"
    }
    val user2 = user1
    user1 = object : Nameable {
        override var name: String = "Bartek"
    }
    println(user2.name)
}
```


```
//7
fun main() {
    var list1 = listOf(1, 2, 3)
    var list2 = list1
    list1 += 4
    println(list2)
}
```


```
//8
fun main() {
    val list1 = mutableListOf(1, 2, 3)
    val list2 = list1
    list1 += 4
    println(list2)
}
```


```
//9
fun main() {
    var map = mapOf("a" to 10)
    val a by map
    map = mapOf("a" to 20)
    println(a)
}
```


```
//10
fun main() {
    val mmap = mutableMapOf("a" to 10)
    val a by mmap
    mmap["a"] = 20
    println(a)
}
```


```
//11
var map = mapOf("a" to 10)
// val a by map
// is compiled to
val `a$delegate` = map
val a: Int
    get() = `a$delegate`.getValue(null, ::a)

val mmap = mutableMapOf("b" to 10)
// val b by mmap
// is compiled to
val `b$delegate` = mmap
val b: Int
    get() = `b$delegate`.getValue(null, ::b)

fun main() {
    map = mapOf("a" to 20)
    println(a) // 10

    mmap["b"] = 20
    println(b) // 20
}
```


```
//12
class Population(var cities: Map<String, Int>) {
    val sanFrancisco by cities
    val tallinn by cities
    val kotlin by cities
}

val population = Population(
    mapOf(
        "sanFrancisco" to 864_816,
        "tallinn" to 413_782,
        "kotlin" to 43_005
    )
)

fun main(args: Array<String>) {
    // Years has passed,
    // now we all live on Mars
    population.cities = emptyMap()
    println(population.sanFrancisco)
    println(population.tallinn)
    println(population.kotlin)
}
```