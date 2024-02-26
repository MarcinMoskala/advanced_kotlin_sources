package f_01_generics_variance_2.s_2

sealed class LinkedList<out T>
data class Node<T>(
    val head: T, 
    val tail: LinkedList<T>
) : LinkedList<T>()
object Empty : LinkedList<Nothing>()

fun main() {
    val strs = Node("A", Node("B", Empty))
    val ints = Node(1, Node(2, Empty))
    val empty: LinkedList<Char> = Empty
}
