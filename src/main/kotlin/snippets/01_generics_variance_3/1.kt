package f_01_generics_variance_3.s_1

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
