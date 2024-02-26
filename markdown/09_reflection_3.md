```
//1
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun main() {
    val t1: KType = typeOf<Int?>()
    println(t1) // kotlin.Int?
    val t2: KType = typeOf<List<Int?>>()
    println(t2) // kotlin.collections.List<kotlin.Int?>
    val t3: KType = typeOf<() -> Map<Int, Char?>>()
    println(t3)
    // () -> kotlin.collections.Map<kotlin.Int, kotlin.Char?>
}
```


```
// Simplified KType definition
interface KType : KAnnotatedElement {
    val isMarkedNullable: Boolean
    val arguments: List<KTypeProjection>
    val classifier: KClassifier?
}
```


```
//2
import kotlin.reflect.typeOf

fun main() {
    println(typeOf<Int>().isMarkedNullable) // false
    println(typeOf<Int?>().isMarkedNullable) // true
}
```


```
// Simplified KTypeProjection definition
data class KTypeProjection(
    val variance: KVariance?,
    val type: KType?
)
```


```
//3
import kotlin.reflect.typeOf

class Box<T>

fun main() {
    val t1 = typeOf<List<Int>>()
    println(t1.arguments) // [kotlin.Int]
    val t2 = typeOf<Map<Long, Char>>()
    println(t2.arguments) // [kotlin.Long, kotlin.Char]
    val t3 = typeOf<Box<out String>>()
    println(t3.arguments) // [out kotlin.String]
}
```


```
//4
import kotlin.reflect.*

class Box<T>(val value: T) {
    fun get(): T = value
}

fun main() {
    val t1 = typeOf<List<Int>>()
    println(t1.classifier) // class kotlin.collections.List
    println(t1 is KType) // true
    println(t1 is KClass<*>) // false
    val t2 = typeOf<Map<Long, Char>>()
    println(t2.classifier) // class kotlin.collections.Map
    println(t2.arguments[0].type?.classifier)
    // class kotlin.Long

    val t3 = Box<Int>::get.returnType.classifier
    println(t3) // T
    println(t3 is KTypeParameter) // true
}
```


```
// KTypeParameter definition
interface KTypeParameter : KClassifier {
    val name: String
    val upperBounds: List<KType>
    val variance: KVariance
    val isReified: Boolean
}
```


```
class ValueGenerator(
    private val random: Random = Random,
) {
    inline fun <reified T> randomValue(): T =
        randomValue(typeOf<T>()) as T

    fun randomValue(type: KType): Any? = TODO()
}
```


```
import kotlin.random.Random
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class RandomValueConfig(
    val nullProbability: Double = 0.1,
)

class ValueGenerator(
    private val random: Random = Random,
    val config: RandomValueConfig = RandomValueConfig(),
) {

    inline fun <reified T> randomValue(): T =
        randomValue(typeOf<T>()) as T

    fun randomValue(type: KType): Any? = when {
        type.isMarkedNullable -> randomNullable(type)
        // ...
        else -> error("Type $type not supported")
    }

    private fun randomNullable(type: KType) =
        if (randomBoolean(config.nullProbability)) null
        else randomValue(type.withNullability(false))

    private fun randomBoolean(probability: Double) =
        random.nextDouble() < probability
}
```


```
//5
import kotlin.math.ln
import kotlin.random.Random
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf

class RandomValueConfig(
    val nullProbability: Double = 0.1,
    val zeroProbability: Double = 0.1,
)

class ValueGenerator(
    private val random: Random = Random,
    val config: RandomValueConfig = RandomValueConfig(),
) {

    inline fun <reified T> randomValue(): T =
        randomValue(typeOf<T>()) as T

    fun randomValue(type: KType): Any? = when {
        type.isMarkedNullable &&
            randomBoolean(config.nullProbability) -> null
        type == typeOf<Boolean>() -> randomBoolean()
        type == typeOf<Int>() -> randomInt()
        // ...
        else -> error("Type $type not supported")
    }

    private fun randomInt() =
        if (randomBoolean(config.zeroProbability)) 0
        else random.nextInt()
    
    private fun randomBoolean() =
        random.nextBoolean()

    private fun randomBoolean(probability: Double) =
        random.nextDouble() < probability
}
```


```
import kotlin.math.ln
import kotlin.random.Random
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.withNullability
import kotlin.reflect.typeOf

class RandomValueConfig(
    val nullProbability: Double = 0.1,
    val zeroProbability: Double = 0.1,
    val stringSizeParam: Double = 0.1,
    val listSizeParam: Double = 0.3,
)

class ValueGenerator(
    private val random: Random = Random,
    val config: RandomValueConfig = RandomValueConfig(),
) {

    inline fun <reified T> randomValue(): T =
        randomValue(typeOf<T>()) as T
    
    fun randomValue(type: KType): Any? = when {
        type.isMarkedNullable -> randomNullable(type)
        type == typeOf<Boolean>() -> randomBoolean()
        type == typeOf<Int>() -> randomInt()
        type == typeOf<String>() -> randomString()
        type.isSubtypeOf(typeOf<List<*>>()) ->
            randomList(type)
        // ...
        else -> error("Type $type not supported")
    }
    
    private fun randomNullable(type: KType) =
        if (randomBoolean(config.nullProbability)) null
        else randomValue(type.withNullability(false))
    
    private fun randomString(): String =
        (1..random.exponential(config.stringSizeParam))
            .map { CHARACTERS.random(random) }
            .joinToString(separator = "")
    
    private fun randomList(type: KType) =
        List(random.exponential(config.listSizeParam)) {
            randomValue(type.arguments[0].type!!)
        }
    
    private fun randomInt() =
        if (randomBoolean(config.zeroProbability)) 0
        else random.nextInt()
    
    private fun randomBoolean() =
        random.nextBoolean()
    
    private fun randomBoolean(probability: Double) =
        random.nextDouble() < probability
    
    companion object {
        private val CHARACTERS =
            ('A'..'Z') + ('a'..'z') + ('0'..'9') + " "
    }
}

private fun Random.exponential(f: Double): Int {
    return (ln(1 - nextDouble()) / -f).toInt()
}
```


```
fun main() {
    val r = Random(1)
    val g = ValueGenerator(random = r)
    println(g.randomValue<Int>()) // -527218591
    println(g.randomValue<Int?>()) // -2022884062
    println(g.randomValue<Int?>()) // null
    println(g.randomValue<List<Int>>())
    // [-1171478239]
    println(g.randomValue<List<List<Boolean>>>())
    // [[true, true, false], [], [], [false, false], [],
    // [true, true, true, true, true, true, true, false]]
    println(g.randomValue<List<Int?>?>())
    // [-416634648, null, 382227801]
    println(g.randomValue<String>()) // WjMNxTwDPrQ
    println(g.randomValue<List<String?>>())
// [VAg, , null, AIKeGp9Q7, 1dqARHjUjee3i6XZzhQ02l, DlG, , ]
}
```


```
//6
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

class A {
    val a = 123
    fun b() {}
}

fun main() {
    val c1: Class<A> = A::class.java
    val c2: Class<A> = A().javaClass
    
    val f1: Field? = A::a.javaField
    val f2: Method? = A::a.javaGetter
    val f3: Method? = A::b.javaMethod
    
    val kotlinClass: KClass<A> = c1.kotlin
    val kotlinProperty: KProperty<*>? = f1?.kotlinProperty
    val kotlinFunction: KFunction<*>? = f3?.kotlinFunction
}
```


```
//7
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

class A {
    private var value = 0
    private fun printValue() {
        println(value)
    }
    override fun toString(): String =
        "A(value=$value)"
}

fun main() {
    val a = A()
    val c = A::class
    
    // We change value to 999
    val prop = c.declaredMemberProperties
        .find { it.name == "value" } as? KMutableProperty1<A, Int>
    prop?.isAccessible = true
    prop?.set(a, 999)
    println(a) // A(value=999)
    println(prop?.get(a)) // 999
    
    // We call printValue function
    val func: KFunction<*>? = c.declaredMemberFunctions
        .find { it.name == "printValue" }
    func?.isAccessible = true
    func?.call(a) // 999
}
```