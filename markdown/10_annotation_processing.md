```
interface UserRepository {
    fun findUser(userId: String): User?
    fun findUsers(): List<User>
    fun updateUser(user: User)
    fun insertUser(user: User)
}

class MongoUserRepository : UserRepository {
    override fun findUser(userId: String): User? = TODO()
    override fun findUsers(): List<User> = TODO()
    override fun updateUser(user: User) {
        TODO()
    }
    override fun insertUser(user: User) {
        TODO()
    }
}

class FakeUserRepository : UserRepository {
    private var users = listOf<User>()
    
    override fun findUser(userId: String): User? =
        users.find { it.id == userId }
    
    override fun findUsers(): List<User> = users
    
    override fun updateUser(user: User) {
        val oldUsers = users.filter { it.id == user.id }
        users = users - oldUsers + user
    }
    
    override fun insertUser(user: User) {
        users = users + user
    }
}
```


```
@GenerateInterface("UserRepository")
class MongoUserRepository : UserRepository {
    override fun findUser(userId: String): User? = TODO()
    override fun findUsers(): List<User> = TODO()
    override fun updateUser(user: User) {
        TODO()
    }
    override fun insertUser(user: User) {
        TODO()
    }
}

class FakeUserRepository : UserRepository {
    private var users = listOf<User>()
    
    override fun findUser(userId: String): User? =
        users.find { it.id == userId }
    
    override fun findUsers(): List<User> = users
    
    override fun updateUser(user: User) {
        val oldUsers = users.filter { it.id == user.id }
        users = users - oldUsers + user
    }
    
    override fun insertUser(user: User) {
        users = users + user
    }
}
```


```
package academy.kt

import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
annotation class GenerateInterface(val name: String)
```


```
package academy.kt

class GenerateInterfaceProcessor : AbstractProcessor() {
    // ...
}
```


```
class GenerateInterfaceProcessor : AbstractProcessor() {
    
    override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(GenerateInterface::class.qualifiedName!!)
    
    override fun getSupportedSourceVersion(): SourceVersion =
        SourceVersion.latestSupported()
    
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        generateInterfaces(roundEnv)
        return true
    }
    
    private fun generateInterfaces(roundEnv: RoundEnvironment) {
        // ...
    }
}
```


```
private fun generateInterfaces(roundEnv: RoundEnvironment) {
    roundEnv
        .getElementsAnnotatedWith(GenerateInterface::class.java)
        .filterIsInstance<TypeElement>()
        .forEach(::generateInterface)
}

private fun generateInterface(annotatedClass: TypeElement) {
    // ...
}
```


```
val interfaceName = annotatedClass
    .getAnnotation(GenerateInterface::class.java)
    .name
```


```
val interfacePackage = processingEnv
    .elementUtils
    .getPackageOf(annotatedClass)
    .qualifiedName
    .toString()
```


```
val publicMethods = annotatedClass.enclosedElements
    .filter { it.kind == ElementKind.METHOD }
    .filter { Modifier.PUBLIC in it.modifiers }
    .filterIsInstance<ExecutableElement>()
```


```
private fun generateInterface(annotatedClass: TypeElement) {
    val interfaceName = annotatedClass
        .getAnnotation(GenerateInterface::class.java)
        .name
    val interfacePackage = processingEnv
        .elementUtils
        .getPackageOf(annotatedClass)
        .qualifiedName
        .toString()

    val publicMethods = annotatedClass.enclosedElements
        .filter { it.kind == ElementKind.METHOD }
        .filter { Modifier.PUBLIC in it.modifiers }
        .filterIsInstance<ExecutableElement>()

    buildInterfaceFile(
        interfacePackage,
        interfaceName,
        publicMethods
    ).writeTo(processingEnv.filer)
}
```


```
private fun buildInterfaceFile(
    interfacePackage: String,
    interfaceName: String,
    publicMethods: List<ExecutableElement>
): JavaFile = JavaFile.builder(
    interfacePackage,
    buildInterface(interfaceName, publicMethods)
).build()
```


```
private fun buildInterface(
    interfaceName: String,
    publicMethods: List<ExecutableElement>
): TypeSpec = TypeSpec
    .interfaceBuilder(interfaceName)
    .addMethods(publicMethods.map(::buildInterfaceMethod))
    .build()
```


```
private fun buildInterfaceMethod(
    method: ExecutableElement
): MethodSpec = MethodSpec
    .methodBuilder(method.simpleName.toString())
    .addModifiers(method.modifiers)
    .addModifiers(Modifier.ABSTRACT)
    .addParameters(
        method.parameters.map(::buildInterfaceMethodParameter)
    )
    .addAnnotations(
        method.annotationMirrors.map(AnnotationSpec::get)
    )
    .returns(method.returnType.toTypeSpec())
    .build()
```


```
private fun TypeMirror.toTypeSpec() = TypeName.get(this)
    .annotated(this.getAnnotationSpecs())

private fun AnnotatedConstruct.getAnnotationSpecs() =
    annotationMirrors.map(AnnotationSpec::get)
```


```
private fun buildInterfaceMethodParameter(
    variableElement: VariableElement
): ParameterSpec = ParameterSpec
    .builder(
        variableElement.asType().toTypeSpec(),
        variableElement.simpleName.toString()
    )
    .addAnnotations(variableElement.getAnnotationSpecs())
    .build()
```


```
class MockitoInjectMocksExamples {
    
    @Mock
    lateinit var emailService: EmailService
    
    @Mock
    lateinit var smsService: SMSService
    
    @InjectMocks
    lateinit var notificationSender: NotificationSender
    
    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    // ...
}
```


```
@RestController
class WelcomeResource {

    @Value("\${welcome.message}")
    private lateinit var welcomeMessage: String
    
    @Autowired
    private lateinit var configuration: BasicConfiguration
    
    @GetMapping("/welcome")
    fun retrieveWelcomeMessage(): String = welcomeMessage
    
    @RequestMapping("/dynamic-configuration")
    fun dynamicConfiguration(): Map<String, Any?> = mapOf(
        "message" to configuration.message,
        "number" to configuration.number,
        "key" to configuration.isValue,
    )
}
```


```
@SpringBootApplication
open class MyApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MyApp::class.java, *args)
        }
    }
}
```