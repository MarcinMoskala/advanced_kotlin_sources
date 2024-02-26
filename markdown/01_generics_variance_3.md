```
open class Dog
class Puppy : Dog()
class Hound : Dog()

fun takeDog(dog: Dog) {}

takeDog(Dog())
takeDog(Puppy())
takeDog(Hound())
```


```
//1
open class Dog
class Puppy : Dog()
class Hound : Dog()

class Box<in T> {
    private var value: T? = null
    
    fun put(value: T) {
        this.value = value
    }
}

fun main() {
    val dogBox = Box<Dog>()
    dogBox.put(Dog())
    dogBox.put(Puppy())
    dogBox.put(Hound())
    
    val puppyBox: Box<Puppy> = dogBox
    puppyBox.put(Puppy())
    
    val houndBox: Box<Hound> = dogBox
    houndBox.put(Hound())
}
```


```
class Box<out T> {
    private var value: T? = null
    
    fun set(value: T) { // Compilation Error
        this.value = value
    }
    
    fun get(): T = value ?: error("Value not set")
}

val dogHouse = Box<Dog>()
val box: Box<Any> = dogHouse
box.set("Some string")
// Is this were possible, we would have runtime error here
```


```
class Box<out T> {
    private var value: T? = null
    
    private fun set(value: T) { // OK
        this.value = value
    }
    
    fun get(): T = value ?: error("Value not set")
}
```


```
open class Car
interface Boat
class Amphibious : Car(), Boat

fun getAmphibious(): Amphibious = Amphibious()

val amphibious: Amphibious = getAmphibious()
val car: Car = getAmphibious()
val boat: Boat = getAmphibious()
```


```
//2
open class Car
interface Boat
class Amphibious : Car(), Boat

class Producer<out T>(val factory: () -> T) {
    fun produce(): T = factory()
}

fun main() {
    val producer: Producer<Amphibious> = Producer { Amphibious() }
    val amphibious: Amphibious = producer.produce()
    val boat: Boat = producer.produce()
    val car: Car = producer.produce()
    
    val boatProducer: Producer<Boat> = producer
    val boat1: Boat = boatProducer.produce()
    
    val carProducer: Producer<Car> = producer
    val car2: Car = carProducer.produce()
}
```


```
open class Car
interface Boat
class Amphibious : Car(), Boat

class Producer<in T>(val factory: () -> T) {
    fun produce(): T = factory() // Compilation Error
}

fun main() {
    val carProducer = Producer<Amphibious> { Car() }
    val amphibiousProducer: Producer<Amphibious> = carProducer
    val amphibious = amphibiousProducer.produce()
    // If not compilation error, we would have runtime error

    val producer = Producer<Amphibious> { Amphibious() }
    val nothingProducer: Producer<Nothing> = producer
    val str: String = nothingProducer.produce()
    // If not compilation error, we would have runtime error
}
```


```
class Box<in T>(
    val value: T // Compilation Error
) {
    
    fun get(): T = value // Compilation Error
        ?: error("Value not set")
}
```


```
class Box<in T>(
    private val value: T
) {
    
    private fun get(): T = value
        ?: error("Value not set")
}
```


```
public interface Continuation<in T> {
    public val context: CoroutineContext
    public fun resumeWith(result: Result<T>)
}
```


```
class Box<in T1, out T2> {
    var v1: T1 // Compilation error
    var v2: T2 // Compilation error
}
```


```
// Declaration-side variance modifier
class Box<out T>(val value: T)

val boxStr: Box<String> = Box("Str")
val boxAny: Box<Any> = boxStr
```


```
class Box<T>(val value: T)

val boxStr: Box<String> = Box("Str")
// Use-site variance modifier
val boxAny: Box<out Any> = boxStr
```


```
//3
interface Dog
interface Pet
data class Puppy(val name: String) : Dog, Pet
data class Wolf(val name: String) : Dog
data class Cat(val name: String) : Pet

fun fillWithPuppies(list: MutableList<in Puppy>) {
    list.add(Puppy("Jim"))
    list.add(Puppy("Beam"))
}

fun main() {
    val dogs = mutableListOf<Dog>(Wolf("Pluto"))
    fillWithPuppies(dogs)
    println(dogs)
    // [Wolf(name=Pluto), Puppy(name=Jim), Puppy(name=Beam)]
    
    val pets = mutableListOf<Pet>(Cat("Felix"))
    fillWithPuppies(pets)
    println(pets)
    // [Cat(name=Felix), Puppy(name=Jim), Puppy(name=Beam)]
}
```


```
if (value is List<*>) {
    ...
}
```