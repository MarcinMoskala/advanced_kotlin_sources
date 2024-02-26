package f_09_reflection_2.s_13

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
