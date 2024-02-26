```
@kotlin.internal.InlineOnly
public inline fun CharSequence?.isNullOrBlank(): Boolean {
    contract {
        returns(false) implies (this@isNullOrBlank != null)
    }
    
    return this == null || this.isBlank()
}

public inline fun measureTimeMillis(block: () -> Unit): Long {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
```


```
@ContractsDsl
@ExperimentalContracts
@InlineOnly
@SinceKotlin("1.3")
@Suppress("UNUSED_PARAMETER")
inline fun contract(builder: ContractBuilder.() -> Unit) {}
```


```
fun mul(x: Int, y: Int): Int {
    require(x > 0)
    require(y > 0)
    return x * y
}
```


```
@kotlin.internal.InlineOnly
public inline fun <R> run(block: () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}
```


```
//1
fun main() {
    val i: Int
    i = 42
    println(i) // 42
}
```


```
//2
fun main() {
    val i: Int
    run {
        i = 42
    }
    println(i) // 42
}
```


```
suspend fun <T, R> forceExecutionTimeMillis(
    timeMillis: Long, block: () -> R
): R {
    val result: R
    val timeTaken = measureTimeMillis {
        result = block()
    }
    val timeLeft = timeMillis - timeTaken
    delay(timeLeft)
    return result
}

@OptIn(ExperimentalContracts::class)
inline fun measureTimeMillis(block: () -> Unit): Long {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
```


```
@OptIn(ExperimentalContracts::class)
fun checkTextEverySecond(callback: (String) -> Unit) {
    contract {
        callsInPlace(callback, InvocationKind.AT_LEAST_ONCE)
    }
    val task = object : TimerTask() {
        override fun run() {
            callback(getCurrentText())
        }
    }
    task.run()
    Timer().schedule(task, 1000, 1000)
}

fun main() {
    var text: String
    checkTextEverySecond {
        text = it
    }
    println(text)
}
```


```
//3
fun main() {
    run {
        println("A")
        return
        println("B") // unreachable
    }
    println("C") // unreachable
}
```


```
fun makeDialog(): Dialog {
    DialogBuilder().apply {
        title = "Alert"
        setPositiveButton("OK") { /*...*/ }
        setNegativeButton("Cancel") { /*...*/ }
        return create()
    }
}

fun readFirstLine(): String? = File("XYZ")
    .useLines {
        return it.firstOrNull()
    }
```


```
inline fun <T> Collection<T>?.isNullOrEmpty(): Boolean {
   contract {
       returns(false) implies (this@isNullOrEmpty != null)
   }
   
   return this == null || this.isEmpty()
}

fun printEachLine(list: List<String>?) {
   if (!list.isNullOrEmpty()) { 
       for (e in list) { //list smart-casted to List<String>
           println(e)
       }
   }
}
```


```
@OptIn(ExperimentalContracts::class)
fun VideoState.startedLoading(): Boolean {
    contract {
      returns(true) implies (this@startedLoading is Loading)
    }
    return this is Loading && this.progress > 0
}
```


```
@OptIn(ExperimentalContracts::class)
suspend fun measureCoroutineDuration(
    body: suspend () -> Unit
): Duration {
    contract {
        callsInPlace(body, InvocationKind.EXACTLY_ONCE)
    }
    val dispatcher = coroutineContext[ContinuationInterceptor]
    return if (dispatcher is TestDispatcher) {
        val before = dispatcher.scheduler.currentTime
        body()
        val after = dispatcher.scheduler.currentTime
        after - before
    } else {
        measureTimeMillis {
            body()
        }
    }.milliseconds
}

@OptIn(ExperimentalContracts::class)
suspend fun <T> measureCoroutineTimedValue(
    body: suspend () -> T
): TimedValue<T> {
    contract {
        callsInPlace(body, InvocationKind.EXACTLY_ONCE)
    }
    var value: T
    val duration = measureCoroutineDuration {
        value = body()
    }
    return TimedValue(value, duration)
}
```