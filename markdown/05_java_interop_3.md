```
fun List<Long>.average() = sum().toDouble() / size
fun List<Int>.average() = sum().toDouble() / size
```


```
//1
@JvmName("averageLongList")
fun List<Long>.average() = sum().toDouble() / size

@JvmName("averageIntList")
fun List<Int>.average() = sum().toDouble() / size

fun main() {
    val ints: List<Int> = List(10) { it }
    println(ints.average()) // 4.5
    val longs: List<Long> = List(10) { it.toLong() }
    println(longs.average()) // 4.5
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        List<Integer> ints = List.of(1, 2, 3);
        double res1 = TestKt.averageIntList(ints);
        System.out.println(res1); // 2.0
        List<Long> longs = List.of(1L, 2L, 3L);
        double res2 = TestKt.averageLongList(longs);
        System.out.println(res2); // 2.0
    }
}
```


```
package test

const val e = 2.71

fun add(a: Int, b: Int) = a + b
```


```
// Compiles to the analog of the following Java code
package test;

public final class TestKt {
    public static final double e = 2.71;
    
    public static final int add(int a, int b) {
        return a + b;
    }
}
```


```
// Usage from Java
public class JavaClass {
    public static void main(String[] args) {
        System.out.println(TestKt.e); // 2.71
        int res = TestKt.add(1, 2);
        System.out.println(res); // 3
    }
}
```


```
@file:JvmName("Math")

package test

const val e = 2.71

fun add(a: Int, b: Int) = a + b
```


```
// Compiles to the analog of the following Java code
package test;

public final class Math {
    public static final double e = 2.71;
    
    public static final int add(int a, int b) {
        return a + b;
    }
}
```


```
// Usage from Java
public class JavaClass {
    public static void main(String[] args) {
        System.out.println(Math.e); // 2.71
        int res = Math.add(1, 2);
        System.out.println(res); // 3
    }
}
```


```
// FooUtils.kt
@file:JvmName("Utils")
@file:JvmMultifileClass

package demo

fun foo() {
    // ...
}
```


```
// BarUtils.kt
@file:JvmName("Utils")
@file:JvmMultifileClass

package demo

fun bar() {
    // ...
}
```


```
// Usage from Java

import demo.Utils;

public class JavaClass {
    public static void main(String[] args) {
        Utils.foo();
        Utils.bar();
    }
}
```


```
//2
class Pizza(
    val tomatoSauce: Int = 1,
    val cheese: Int = 0,
    val ham: Int = 0,
    val onion: Int = 0,
)

class EmailSender {
    fun send(
        receiver: String,
        title: String = "",
        message: String = "",
    ) { 
        /*...*/
    }
}
```


```
//3
class Pizza @JvmOverloads constructor(
    val tomatoSauce: Int = 1,
    val cheese: Int = 0,
    val ham: Int = 0,
    val onion: Int = 0,
)

class EmailSender {
    @JvmOverloads
    fun send(
        receiver: String,
        title: String = "",
        message: String = "",
    ) {
        /*...*/
    }
}
```