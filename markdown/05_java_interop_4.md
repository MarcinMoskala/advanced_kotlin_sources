```
//1
fun a() {}

fun main() {
    println(a()) // kotlin.Unit
}
```


```
//2
// Kotlin code
fun a(): Unit {
    return Unit
}

fun main() {
    println(a()) // kotlin.Unit
}
```


```
class ListAdapter {

    fun setListItemListener(
        listener: (
            position: Int,
            id: Int,
            child: View,
            parent: View
        ) -> Unit
    ) {
        // ...
    }

    // ...
}

// Usage
fun usage() {
    val a = ListAdapter()
    a.setListItemListener { position, id, child, parent ->
        // ...
    }
}
```


```
fun interface ListItemListener {
    fun handle(
        position: Int,
        id: Int,
        child: View,
        parent: View
    )
}

class ListAdapter {
    
    fun setListItemListener(listener: ListItemListener) {
        // ...
    }
    
    // ...
}

fun usage() {
    val a = ListAdapter()
    a.setListItemListener { position, id, child, parent ->
        // ...
    }
}
```


```
// Example Mockito usage
val mock = mock(UserService::class.java)
`when`(mock.getUser("1")).thenAnswer { aUser }
```


```
class MarkdownToHtmlTest {
    
    @Test
    fun `Simple text should remain unchanged`() {
        val text = "Lorem ipsum"
        val result = markdownToHtml(text)
        assertEquals(text, result)
    }
}
```


```
public class JavaClass {
  // IOException are checked exceptions,
  // and they must be declared with throws
  String readFirstLine(String fileName) throws IOException {
    FileInputStream fis = new FileInputStream(fileName);
    InputStreamReader reader = new InputStreamReader(fis);
    BufferedReader bufferedReader = new BufferedReader(reader);
    return bufferedReader.readLine();
  }
  
  void checkFirstLine() {
    String line;
    try {
      line = readFirstLine("number.txt");
      // We must catch checked exceptions, 
      // or declare them with throws
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // parseInt throws NumberFormatException,
    // which is an unchecked exception
    int number = Integer.parseInt(line);
    // Dividing two numbers might throw
    // ArithmeticException of number is 0,
    // which is an unchecked exception
    System.out.println(10 / number);
  }
}
```


```
// Kotlin
@file:JvmName("FileUtils")

package test

import java.io.*

fun readFirstLine(fileName: String): String =
    File(fileName).useLines { it.first() }
```


```
// Kotlin
@file:JvmName("FileUtils")

package test

import java.io.*

@Throws(IOException::class)
fun readFirstLine(fileName: String): String =
    File(fileName).useLines { it.first() }
```


```
@JvmRecord
data class Person(val name: String, val age: Int)
```