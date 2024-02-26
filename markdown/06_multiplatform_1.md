```
// commonMain
expect object Platform {
    val name: String
}

// jvmMain
actual object Platform {
    actual val name: String = "JVM"
}

// jsMain
actual object Platform {
    actual val name: String = "JS"
}
```


```
// commonMain
expect class DateTime {
    fun getHour(): Int
    fun getMinute(): Int
    fun getSecond(): Int
    // ...
}

// jvmMain
actual typealias DateTime = LocalDateTime

// jsMain
import kotlin.js.Date
        
actual class DateTime(
    val date: Date = Date()
) {
    actual fun getHour(): Int = date.getHours()
    actual fun getMinute(): Int = date.getMinutes()
    actual fun getSecond(): Int = date.getSeconds()
}
```