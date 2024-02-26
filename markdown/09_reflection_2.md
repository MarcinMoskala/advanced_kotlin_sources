```
//1
import kotlin.reflect.KClass

class A

fun main() {
    val class1: KClass<A> = A::class
    println(class1) // class A
    
    val a: A = A()
    val class2: KClass<out A> = a::class
    println(class2) // class A
}
```


```
//2
import kotlin.reflect.KClass

open class A
class B : A()

fun main() {
    val a: A = B()
    val clazz: KClass<out A> = a::class
    println(clazz) // class B
}
```


```
//3
package a.b.c

class D {
    class E
}

fun main() {
    val clazz = D.E::class
    println(clazz.simpleName) // E
    println(clazz.qualifiedName) // a.b.c.D.E
}
```


```
//4
fun main() {
    val o = object {}
    val clazz = o::class
    println(clazz.simpleName) // null
    println(clazz.qualifiedName) // null
}
```


```
//5
sealed class UserMessages

private data class UserId(val id: String) {
    companion object {
        val ZERO = UserId("")
    }
}

internal fun interface Filter<T> {
    fun check(value: T): Boolean
}

fun main() {
    println(UserMessages::class.visibility) // PUBLIC
    println(UserMessages::class.isSealed) // true
    println(UserMessages::class.isOpen) // false
    println(UserMessages::class.isFinal) // false
    println(UserMessages::class.isAbstract) // false

    println(UserId::class.visibility) // PRIVATE
    println(UserId::class.isData) // true
    println(UserId::class.isFinal) // true

    println(UserId.Companion::class.isCompanion) // true
    println(UserId.Companion::class.isInner) // false

    println(Filter::class.visibility) // INTERNAL
    println(Filter::class.isFun) // true
}
```


```
//6
import kotlin.reflect.full.*

open class Parent {
    val a = 12
    fun b() {}
}

class Child : Parent() {
    val c = 12
    fun d() {}
}

fun Child.e() {}

fun main() {
    println(Child::class.members.map { it.name })
    // [c, d, a, b, equals, hashCode, toString]
    println(Child::class.functions.map { it.name })
    // [d, b, equals, hashCode, toString]
    println(Child::class.memberProperties.map { it.name })
    // [c, a]

    println(Child::class.declaredMembers.map { it.name })
    // [c, d]
    println(Child::class.declaredFunctions.map { it.name }) 
    // [d]
    println(Child::class.declaredMemberProperties.map { it.name })
    // [c]
}
```


```
//7
package playground

import kotlin.reflect.KFunction

class User(val name: String) {
    constructor(user: User) : this(user.name)
    constructor(json: UserJson) : this(json.name)
}

class UserJson(val name: String)

fun main() {
    val constructors: Collection<KFunction<User>> =
        User::class.constructors
    
    println(constructors.size) // 3
    constructors.forEach(::println)
    // fun <init>(playground.User): playground.User
    // fun <init>(playground.UserJson): playground.User
    // fun <init>(kotlin.String): playground.User
}
```


```
//8
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

interface I1
interface I2
open class A : I1
class B : A(), I2

fun main() {
    val a = A::class
    val b = B::class
    println(a.superclasses) // [class I1, class kotlin.Any]
    println(b.superclasses) // [class A, class I2]
    println(a.supertypes) // [I1, kotlin.Any]
    println(b.supertypes) // [A, I2]
}
```


```
//9
interface I1
interface I2
open class A : I1
class B : A(), I2

fun main() {
    val a = A()
    val b = B()
    println(A::class.isInstance(a)) // true
    println(B::class.isInstance(a)) // false
    println(I1::class.isInstance(a)) // true
    println(I2::class.isInstance(a)) // false
    
    println(A::class.isInstance(b)) // true
    println(B::class.isInstance(b)) // true
    println(I1::class.isInstance(b)) // true
    println(I2::class.isInstance(b)) // true
}
```


```
//10
fun main() {
    println(List::class.typeParameters) // [out E]
    println(Map::class.typeParameters) // [K, out V]
}
```


```
//11
class A {
    class B
    inner class C
}

fun main() {
    println(A::class.nestedClasses) // [class A$B, class A$C]
}
```


```
//12
sealed class LinkedList<out T>

class Node<out T>(
    val head: T,
    val next: LinkedList<T>
) : LinkedList<T>()

object Empty : LinkedList<Nothing>()

fun main() {
    println(LinkedList::class.sealedSubclasses)
    // [class Node, class Empty]
}
```


```
//13
import kotlin.reflect.KClass

sealed class LinkedList<out T>

data class Node<out T>(
    val head: T,
    val next: LinkedList<T>
) : LinkedList<T>()

data object Empty : LinkedList<Nothing>()

fun main() {
    println(Node::class.objectInstance) // null
    println(Empty::class.objectInstance) // Empty
}
```


```
class Creature(
    val name: String,
    val attack: Int,
    val defence: Int,
)

fun main() {
    val creature = Creature(
        name = "Cockatrice",
        attack = 2,
        defence = 4
    )
    println(creature.toJson())
    // {"attack": 2, "defence": 4, "name": "Cockatrice"}
}
```


```
fun Any.toJson(): String = objectToJson(this)

private fun objectToJson(any: Any) = any::class
    .memberProperties
    .joinToString(
        prefix = "{",
        postfix = "}",
        transform = { prop ->
            "\"${prop.name}\": ${valueToJson(prop.call(any))}"
        }
    )
```


```
private fun valueToJson(value: Any?): String = when (value) {
    null, is Number -> "$value"
    is String, is Enum<*> -> "\"$value\""
    // ...
    else -> objectToJson(value)
}
```


```
import kotlin.reflect.full.memberProperties

// Serialization function definition
fun Any.toJson(): String = objectToJson(this)

private fun objectToJson(any: Any) = any::class
    .memberProperties
    .joinToString(
        prefix = "{",
        postfix = "}",
        transform = { prop ->
            "\"${prop.name}\": ${valueToJson(prop.call(any))}"
        }
    )

private fun valueToJson(value: Any?): String = when (value) {
    null, is Number, is Boolean -> "$value"
    is String, is Enum<*> -> "\"$value\""
    is Iterable<*> -> iterableToJson(value)
    is Map<*, *> -> mapToJson(value)
    else -> objectToJson(value)
}

private fun iterableToJson(any: Iterable<*>): String = any
    .joinToString(
        prefix = "[",
        postfix = "]",
        transform = ::valueToJson
    )

private fun mapToJson(any: Map<*, *>) = any.toList()
    .joinToString(
        prefix = "{",
        postfix = "}",
        transform = {
            "\"${it.first}\": ${valueToJson(it.second)}"
        }
    )

// Example use
class Creature(
    val name: String,
    val attack: Int,
    val defence: Int,
    val traits: List<Trait>,
    val cost: Map<Element, Int>
)
enum class Element {
    FOREST, ANY,
}
enum class Trait {
    FLYING
}

fun main() {
    val creature = Creature(
        name = "Cockatrice",
        attack = 2,
        defence = 4,
        traits = listOf(Trait.FLYING),
        cost = mapOf(
            Element.ANY to 3,
            Element.FOREST to 2
        )
    )
    println(creature.toJson())
    // {"attack": 2, "cost": {"ANY": 3, "FOREST": 2}, 
    // "defence": 4, "name": "Cockatrice", 
    // "traits": ["FLYING"]}
}
```


```
// Annotations
@Target(AnnotationTarget.PROPERTY)
annotation class JsonName(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class JsonIgnore

// Example use
class Creature(
    @JsonIgnore val name: String,
    @JsonName("att") val attack: Int,
    @JsonName("def") val defence: Int,
    val traits: List<Trait>,
    val cost: Map<Element, Int>
)
enum class Element {
    FOREST, ANY,
}
enum class Trait {
    FLYING
}

fun main() {
    val creature = Creature(
        name = "Cockatrice",
        attack = 2,
        defence = 4,
        traits = listOf(Trait.FLYING),
        cost = mapOf(
            Element.ANY to 3,
            Element.FOREST to 2
        )
    )
    println(creature.toJson())
    // {"att": 2, "cost": {"ANY": 3, "FOREST": 2}, 
    // "def": 4, "traits": ["FLYING"]}
}
```


```
private fun objectToJson(any: Any) = any::class
    .memberProperties
    .filterNot { it.hasAnnotation<JsonIgnore>() }
    .joinToString(
        prefix = "{",
        postfix = "}",
        transform = { prop ->
            val annotation = prop.findAnnotation<JsonName>()
            val name = annotation?.name ?: prop.name
            "\"${name}\": ${valueToJson(prop.call(any))}"
        }
    )
```