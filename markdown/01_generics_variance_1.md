```
class Box<T>
open class Dog
class Puppy : Dog()

fun main() {
    val d: Dog = Puppy() // Puppy is a subtype of Dog

    val bd: Box<Dog> = Box<Puppy>() // Error: Type mismatch
    val bp: Box<Puppy> = Box<Dog>() // Error: Type mismatch

    val bn: Box<Number> = Box<Int>() // Error: Type mismatch
    val bi: Box<Int> = Box<Number>() // Error: Type mismatch
}
```


```
class Box<out T>
open class Dog
class Puppy : Dog()

fun main() {
    val d: Dog = Puppy() // Puppy is a subtype of Dog

    val bd: Box<Dog> = Box<Puppy>() // OK
    val bp: Box<Puppy> = Box<Dog>() // Error: Type mismatch

    val bn: Box<Number> = Box<Int>() // OK
    val bi: Box<Int> = Box<Number>() // Error: Type mismatch
}
```


```
class Box<in T>
open class Dog
class Puppy : Dog()

fun main() {
    val d: Dog = Puppy() // Puppy is a subtype of Dog
    
    val bd: Box<Dog> = Box<Puppy>() // Error: Type mismatch
    val bp: Box<Puppy> = Box<Dog>() // OK
    
    val bn: Box<Number> = Box<Int>() // Error: Type mismatch
    val bi: Box<Int> = Box<Number>() // OK
}
```


```
//1
interface Animal {
    fun pet()
}

class Cat(val name: String) : Animal {
    override fun pet() {
        println("$name says Meow")
    }
}

fun petAnimals(animals: List<Animal>) {
    for (animal in animals) {
        animal.pet()
    }
}

fun main() {
    val cats: List<Cat> = listOf(Cat("Mruczek"), Cat("Puszek"))
    petAnimals(cats) // Can I do that?
}
```


```
interface Animal
class Cat(val name: String) : Animal
class Dog(val name: String) : Animal

fun addAnimal(animals: MutableList<Animal>) {
    animals.add(Dog("Cookie"))
}

fun main() {
    val cats: MutableList<Cat> =
        mutableListOf(Cat("Mruczek"), Cat("Puszek"))
    addAnimal(cats) // COMPILATION ERROR
    val cat: Cat = cats.last()
    // If code would compile, it would break here
}
```


```
interface Sender<T : Message> {
    fun send(message: T)
}

interface Message

interface OrderManagerMessage : Message
class AddOrder(val order: Order) : OrderManagerMessage
class CancelOrder(val orderId: String) : OrderManagerMessage

interface InvoiceManagerMessage : Message
class MakeInvoice(val order: Order) : OrderManagerMessage
```


```
class GeneralSender(
    serviceUrl: String
) : Sender<Message> {
    private val connection = makeConnection(serviceUrl)
    
    override fun send(message: Message) {
        connection.send(message.toApi())
    }
}

val orderManagerSender: Sender<OrderManagerMessage> =
    GeneralSender(ORDER_MANAGER_URL)

val invoiceManagerSender: Sender<InvoiceManagerMessage> =
    GeneralSender(INVOICE_MANAGER_URL)
```


```
interface Sender<in T : Message> {
    fun send(message: T)
}
```


```
//2
class Consumer<in T> {
    fun consume(value: T) {
        println("Consuming $value")
    }
}

fun main() {
    val numberConsumer: Consumer<Number> = Consumer()
    numberConsumer.consume(2.71) // Consuming 2.71
    val intConsumer: Consumer<Int> = numberConsumer
    intConsumer.consume(42) // Consuming 42
    val floatConsumer: Consumer<Float> = numberConsumer
    floatConsumer.consume(3.14F) // Consuming 3.14
    
    val anyConsumer: Consumer<Any> = Consumer()
    anyConsumer.consume(123456789L) // Consuming 123456789
    val stringConsumer: Consumer<String> = anyConsumer
    stringConsumer.consume("ABC") // Consuming ABC
    val charConsumer: Consumer<Char> = anyConsumer
    charConsumer.consume('M') // Consuming M
}
```


```
fun printProcessedNumber(transformation: (Int) -> Any) {
    println(transformation(42))
}
```


```
val intToDouble: (Int) -> Number = { it.toDouble() }
val numberAsText: (Number) -> String = { it.toString() }
val identity: (Number) -> Number = { it }
val numberToInt: (Number) -> Int = { it.toInt() }
val numberHash: (Any) -> Number = { it.hashCode() }
printProcessedNumber(intToDouble)
printProcessedNumber(numberAsText)
printProcessedNumber(identity)
printProcessedNumber(numberToInt)
printProcessedNumber(numberHash)
```