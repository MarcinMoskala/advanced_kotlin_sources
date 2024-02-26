```
//1
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
```


```
sealed class LinkedList<T>
data class Node<T>(
    val head: T, 
    val tail: LinkedList<T>
) : LinkedList<T>()
object Empty<T> : LinkedList<T>() // Error
```


```
//2
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
```


```
//3
fun main() {
    val empty: List<Nothing> = emptyList()
    val strs: List<String> = empty
    val ints: List<Int> = empty

    val other: List<Char> = emptyList()
    println(empty === other) // true
}
```


```
sealed interface ChangesTrackerMessage<out T>
data class Change<T>(val newValue: T) : ChangesTrackerMessage<T>
data object Reset : ChangesTrackerMessage<Nothing>
data object UndoChange : ChangesTrackerMessage<Nothing>

sealed interface SchedulerMessage<out T>
data class Schedule<T>(val task: Task<T>) : SchedulerMessage<T>
data class Delete(val taskId: String) : SchedulerMessage<Nothing>
data object StartScheduled : SchedulerMessage<Nothing>
data object Reset : SchedulerMessage<Nothing>
```


```
data class Task<T>(
    val id: String,
    val scheduleAt: Instant,
    val data: T,
    val priority: Int,
    val maxRetries: Int? = null
)
```


```
data class TaskUpdate<T>(
    val id: String? = null,
    val scheduleAt: Instant? = null,
    val data: T? = null,
    val priority: Int? = null,
    val maxRetries: Int? = null
)
```


```
data class TaskUpdate<T>(
    val id: TaskPropertyUpdate<String> = Keep,
    val scheduleAt: TaskPropertyUpdate<Instant> = Keep,
    val data: TaskPropertyUpdate<T> = Keep,
    val priority: TaskPropertyUpdate<Int> = Keep,
    val maxRetries: TaskPropertyUpdate<Int?> = Keep
)

sealed interface TaskPropertyUpdate<out T>
data object Keep : TaskPropertyUpdate<Nothing>
data class ChangeTo<T>(val newValue: T) : TaskPropertyUpdate<T>

val update = TaskUpdate<String>(
    id = ChangeTo("456"),
    maxRetries = ChangeTo(null), // we can change to null
    
    data = ChangeTo(123), // COMPILATION ERROR
    // type mismatch, expecting String
    priority = ChangeTo(null), // COMPILATION ERROR
    // type mismatch, property is not nullable
)
```


```
data class TaskUpdate<T>(
    val id: TaskPropertyUpdate<String> = Keep,
    val scheduleAt: TaskPropertyUpdate<Instant> = Keep,
    val data: TaskPropertyUpdate<T> = Keep,
    val priority: TaskPropertyUpdate<Int> = Keep,
    val maxRetries: TaskPropertyUpdate<Int?> = Keep
)

sealed interface TaskPropertyUpdate<out T>
data object Keep : TaskPropertyUpdate<Nothing>
data class ChangeTo<T>(val newValue: T) : TaskPropertyUpdate<T>
data object RestorePrevious : TaskPropertyUpdate<Nothing>
data object RestoreDefault : TaskPropertyUpdate<Nothing>

val update = TaskUpdate<String>(
    data = ChangeTo("ABC"),

    maxRetries = RestorePrevious,
    priority = RestoreDefault,
)
```


```
//4
sealed class Either<out L, out R>
data class Left<out L>(val value: L) : Either<L, Nothing>()
data class Right<out R>(val value: R) : Either<Nothing, R>()
```


```
val left = Left(Error())
val right = Right("ABC")
```


```
val leftError: Left<Error> = Left(Error())
val leftThrowable: Left<Throwable> = leftError
val leftAny: Left<Any> = leftThrowable

val rightInt = Right(123)
val rightNumber: Right<Number> = rightInt
val rightAny: Right<Any> = rightNumber
```


```
val leftError: Left<Error> = Left(Error())
val rightInt = Right(123)

val el: Either<Error, Int> = leftError
val er: Either<Error, Int> = rightInt

val etnl: Either<Throwable, Number> = leftError
val etnr: Either<Throwable, Number> = rightInt
```