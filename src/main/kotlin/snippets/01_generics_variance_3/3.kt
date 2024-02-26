package f_01_generics_variance_3.s_3

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
