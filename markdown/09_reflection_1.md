```
//1
import kotlin.reflect.full.memberProperties

fun displayPropertiesAsList(value: Any) {
    value::class.memberProperties
        .sortedBy { it.name }
        .map { p -> " * ${p.name}: ${p.call(value)}" }
        .forEach(::println)
}

class Person(
    val name: String,
    val surname: String,
    val children: Int,
    val female: Boolean,
)

class Dog(
    val name: String,
    val age: Int,
)

enum class DogBreed {
    HUSKY, LABRADOR, PUG, BORDER_COLLIE
}

fun main() {
    val granny = Person("Esmeralda", "Weatherwax", 0, true)
    displayPropertiesAsList(granny)
    // * children: 0
    // * female: true
    // * name: Esmeralda
    // * surname: Weatherwax

    val cookie = Dog("Cookie", 1)
    displayPropertiesAsList(cookie)
    // * age: 1
    // * name: Cookie

    displayPropertiesAsList(DogBreed.BORDER_COLLIE)
    // * name: BORDER_COLLIE
    // * ordinal: 3
}
```


```
data class Car(val brand: String, val doors: Int)

fun main() {
    val json = "{\"brand\":\"Jeep\", \"doors\": 3}"
    val gson = Gson()
    val car: Car = gson.fromJson(json, Car::class.java)
    println(car) // Car(brand=Jeep, doors=3)
    val newJson = gson.toJson(car)
    println(newJson) // {"brand":"Jeep", "doors": 3}
}
```


```
class MyActivity : Application() {
    val myPresenter: MyPresenter by inject()
}
```


```
interface KAnnotatedElement {
    val annotations: List<Annotation>
}
```


```
import kotlin.reflect.*

fun printABC() {
    println("ABC")
}

fun double(i: Int): Int = i * 2

class Complex(val real: Double, val imaginary: Double) {
    fun plus(number: Number): Complex = Complex(
        real = real + number.toDouble(),
        imaginary = imaginary
    )
}

fun Complex.double(): Complex =
    Complex(real * 2, imaginary * 2)

fun Complex?.isNullOrZero(): Boolean =
    this == null || (this.real == 0.0 && this.imaginary == 0.0)

class Box<T>(var value: T) {
    fun get(): T = value
}

fun <T> Box<T>.set(value: T) {
    this.value = value
}

fun main() {
    val f1 = ::printABC
    val f2 = ::double
    val f3 = Complex::plus
    val f4 = Complex::double
    val f5 = Complex?::isNullOrZero
    val f6 = Box<Int>::get
    val f7 = Box<String>::set
}
```


```
// ...

fun main() {
    val f1: KFunction0<Unit> = ::printABC
    val f2: KFunction1<Int, Int> = ::double
    val f3: KFunction2<Complex, Number, Complex> = Complex::plus
    val f4: KFunction1<Complex, Complex> = Complex::double
    val f5: KFunction1<Complex?, Boolean> = Complex?::isNullOrZero
    val f6: KFunction1<Box<Int>, Int> = Box<Int>::get
    val f7: KFunction2<Box<String>, String, Unit>=Box<String>::set
}
```


```
// ...

fun main() {
    val c = Complex(1.0, 2.0)
    val f3: KFunction1<Number, Complex> = c::plus
    val f4: KFunction0<Complex> = c::double
    val f5: KFunction0<Boolean> = c::isNullOrZero
    val b = Box(123)
    val f6: KFunction0<Int> = b::get
    val f7: KFunction1<Int, Unit> = b::set
}
```


```
// ...

fun main() {
    val f1: KFunction<Unit> = ::printABC
    val f2: KFunction<Int> = ::double
    val f3: KFunction<Complex> = Complex::plus
    val f4: KFunction<Complex> = Complex::double
    val f5: KFunction<Boolean> = Complex?::isNullOrZero
    val f6: KFunction<Int> = Box<Int>::get
    val f7: KFunction<Unit> = Box<String>::set
    val c = Complex(1.0, 2.0)
    val f8: KFunction<Complex> = c::plus
    val f9: KFunction<Complex> = c::double
    val f10: KFunction<Boolean> = c::isNullOrZero
    val b = Box(123)
    val f11: KFunction<Int> = b::get
    val f12: KFunction<Unit> = b::set
}
```


```
// ...

fun nonZeroDoubled(numbers: List<Complex?>): List<Complex?> =
    numbers
        .filterNot(Complex?::isNullOrZero)
        .filterNotNull()
        .map(Complex::double)
```


```
fun nonZeroDoubled(numbers: List<Complex?>): List<Complex?> =
    numbers
        .filterNot { it.isNullOrZero() }
        .filterNotNull()
        .map { it.double() }
```


```
fun add(i: Int, j: Int) = i + j

fun main() {
    val f: KFunction2<Int, Int, Int> = ::add
    println(f(1, 2)) // 3
    println(f.invoke(1, 2)) // 3
}
```


```
import kotlin.reflect.KFunction

inline infix operator fun String.times(times: Int) =
    this.repeat(times)

fun main() {
    val f: KFunction<String> = String::times
    println(f.isInline)   // true
    println(f.isExternal) // false
    println(f.isOperator) // true
    println(f.isInfix)    // true
    println(f.isSuspend)  // false
}
```


```
import kotlin.reflect.KCallable

operator fun String.times(times: Int) = this.repeat(times)

fun main() {
    val f: KCallable<String> = String::times
    println(f.name) // times
    println(f.parameters.map { it.name }) // [null, times]
    println(f.returnType) // kotlin.String
    println(f.typeParameters) // []
    println(f.visibility) // PUBLIC
    println(f.isFinal) // true
    println(f.isOpen) // false
    println(f.isAbstract) // false
    println(f.isSuspend) // false
}
```


```
//2
import kotlin.reflect.KCallable

fun add(i: Int, j: Int) = i + j

fun main() {
    val f: KCallable<Int> = ::add
    println(f.call(1, 2)) // 3
    println(f.call("A", "B")) // IllegalArgumentException
}
```


```
//3
import kotlin.reflect.KCallable

fun sendEmail(
    email: String,
    title: String = "",
    message: String = ""
) {
    println(
        """
       Sending to $email
       Title: $title
       Message: $message
   """.trimIndent()
    )
}

fun main() {
    val f: KCallable<Unit> = ::sendEmail

    f.callBy(mapOf(f.parameters[0] to "ABC"))
    // Sending to ABC
    // Title:
    // Message:

    val params = f.parameters.associateBy { it.name }
    f.callBy(
        mapOf(
            params["title"]!! to "DEF",
            params["message"]!! to "GFI",
            params["email"]!! to "ABC",
        )
    )
    // Sending to ABC
    // Title: DEF
    // Message: GFI

    f.callBy(mapOf()) // throws IllegalArgumentException
}
```


```
import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.typeOf

fun callWithFakeArgs(callable: KCallable<*>) {
    val arguments = callable.parameters
        .filterNot { it.isOptional }
        .associateWith { fakeValueFor(it) }
    callable.callBy(arguments)
}

fun fakeValueFor(parameter: KParameter) =
    when (parameter.type) {
        typeOf<String>() -> "Fake ${parameter.name}"
        typeOf<Int>() -> 123
        else -> error("Unsupported type")
    }

fun sendEmail(
    email: String,
    title: String,
    message: String = ""
) {
    println(
        """
       Sending to $email
       Title: $title
       Message: $message
   """.trimIndent()
    )
}
fun printSum(a: Int, b: Int) {
    println(a + b)
}
fun Int.printProduct(b: Int) {
    println(this * b)
}

fun main() {
    callWithFakeArgs(::sendEmail)
    // Sending to Fake email
    // Title: Fake title
    // Message:
    callWithFakeArgs(::printSum) // 246
    callWithFakeArgs(Int::printProduct) // 15129
}
```


```
//4
import kotlin.reflect.*
import kotlin.reflect.full.memberExtensionProperties

val lock: Any = Any()
var str: String = "ABC"

class Box(
    var value: Int = 0
) {
    val Int.addedToBox
        get() = Box(value + this)
}

fun main() {
    val p1: KProperty0<Any> = ::lock
    println(p1) // val lock: kotlin.Any
    val p2: KMutableProperty0<String> = ::str
    println(p2) // var str: kotlin.String
    val p3: KMutableProperty1<Box, Int> = Box::value
    println(p3) // var Box.value: kotlin.Int
    val p4: KProperty2<Box, *, *> = Box::class
        .memberExtensionProperties
        .first()
    println(p4) // val Box.(kotlin.Int.)addedToBox: Box
}
```


```
//5
import kotlin.reflect.*

class Box(
    var value: Int = 0
)

fun main() {
    val box = Box()
    val p: KMutableProperty1<Box, Int> = Box::value
    println(p.get(box)) // 0
    p.set(box, 999)
    println(p.get(box)) // 999
}
```