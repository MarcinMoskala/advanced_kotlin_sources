package f_01_generics_variance_1.s_2

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
