package f_09_reflection_2.s_12

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
