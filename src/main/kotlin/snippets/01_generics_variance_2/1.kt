package f_01_generics_variance_2.s_1

sealed class LinkedList<T>
data class Node<T>(
    val head: T, 
    val tail: LinkedList<T>
) : LinkedList<T>()
class Empty<T> : LinkedList<T>()

fun main() {
    val strs = Node("A", Node("B", Empty()))
    val ints = Node(1, Node(2, Empty()))
    val empty: LinkedList<Char> = Empty()
}
