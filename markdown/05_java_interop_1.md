```
//1
class MessageSender {
    fun sendMessage(title: String, content: String?) {}
}
```


```
// Compiles to the analog of the following Java code
final class MessageSender {
    public void sendMessage(
        @NotNull String title,
        @Nullable String content
    ) {
        Intrinsics.checkNotNullParameter(title, "title");
    }
}
```


```
public class JavaClass {
    public static @NotNull String produceNonNullable() {
        return "ABC";
    }
    
    public static @Nullable String produceNullable() {
        return null;
    }
}
```


```
// Java
public class UserRepo {
    
    public Observable<List<User>> fetchUsers() {
        //***
    }
}
```


```
// Kotlin, if unannotated types were considered nullable
val repo = UserRepo()
val users: Observable<List<User>> = repo.fetchUsers()!!
    .map { it!!.map { it!! } }
```


```
// Kotlin
val repo = UserRepo()
val user1 = repo.fetchUsers()
// The type of user1 is Observable<List<User!>!>!
val user2: Observable<List<User>> = repo.fetchUsers()
val user3: Observable<List<User?>?>? = repo.fetchUsers()
```


```
//2
// KotlinFile.kt
fun multiply(a: Int, b: Int) = a * b
```


```
// Compiles to the analog of the following Java code
public final class KotlinFileKt {
    public static final int multiply(int a, int b) {
        return a * b;
    }
}
```


```
// Java
public final class JavaClass {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3);
        numbers.add(4); // throws UnsupportedOperationException
    }
}
```


```
//3
// KotlinFile.kt
fun readOnlyList(): List<Int> = listOf(1, 2, 3)
fun mutableList(): MutableList<Int> = mutableListOf(1, 2, 3)
```


```
// Compiles to analog of the following Java code
public final class KotlinFileKt {
    @NotNull
    public static final List readOnlyList() {
        return CollectionsKt.listOf(new Integer[]{1, 2, 3});
    }

    @NotNull
    public static final List mutableList() {
        return CollectionsKt.mutableListOf(new Integer[]{1,2,3});
    }
}
```


```
// Java
public final class JavaClass {
    public static void main(String[] args) {
        List<Integer> integers = KotlinFileKt.readOnlyList();
        integers.add(20); // UnsupportedOperationException
    }
}
```