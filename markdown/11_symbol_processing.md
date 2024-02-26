```
@GenerateInterface("UserRepository")
class MongoUserRepository<T> : UserRepository {
    
    override suspend fun findUser(userId: String): User? = TODO()
    
    override suspend fun findUsers(): List<User> = TODO()
    
    override suspend fun updateUser(user: User) {
        TODO()
    }
    
    @Throws(DuplicatedUserId::class)
    override suspend fun insertUser(user: User) {
        TODO()
    }
}

class FakeUserRepository : UserRepository {
    private var users = listOf<User>()
    
    override suspend fun findUser(userId: String): User? =
        users.find { it.id == userId }
    
    override suspend fun findUsers(): List<User> = users
    
    override suspend fun updateUser(user: User) {
        val oldUsers = users.filter { it.id == user.id }
        users = users - oldUsers + user
    }
    
    override suspend fun insertUser(user: User) {
        if (users.any { it.id == user.id }) {
            throw DuplicatedUserId
        }
        users = users + user
    }
}
```


```
interface UserRepository {
    suspend fun findUser(userId: String): User?
    
    suspend fun findUsers(): List<User>
    
    suspend fun updateUser(user: User)
    
    @Throws(DuplicatedUserId::class)
    suspend fun insertUser(user: User)
}
```


```
package academy.kt

import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
annotation class GenerateInterface(val name: String)
```


```
class GenerateInterfaceProcessorProvider
    : SymbolProcessorProvider {
        
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor =
        GenerateInterfaceProcessor(
            codeGenerator = environment.codeGenerator,
        )
}
```


```
class GenerateInterfaceProcessor(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // ...
        return emptyList()
    }
}
```


```
override fun process(resolver: Resolver): List<KSAnnotated> {
    resolver
        .getSymbolsWithAnnotation(
            GenerateInterface::class.qualifiedName!!
        )
        .filterIsInstance<KSClassDeclaration>()
        .forEach(::generateInterface)
    
    return emptyList()
}

private fun generateInterface(annotatedClass: KSClassDeclaration){
    // ...
}
```


```
val interfaceName = annotatedClass
    .getAnnotationsByType(GenerateInterface::class)
    .single()
    .name
```


```
val interfacePackage = annotatedClass
    .qualifiedName
    ?.getQualifier()
    .orEmpty()
```


```
val publicMethods = annotatedClass
    .getDeclaredFunctions()
    .filter { it.isPublic() && !it.isConstructor() }
```


```
val fileSpec = buildInterfaceFile(
    interfacePackage,
    interfaceName,
    publicMethods
)
val dependencies = Dependencies(
    aggregating = false,
    annotatedClass.containingFile!!
)
fileSpec.writeTo(codeGenerator, dependencies)
```


```
private fun buildInterfaceFile(
    interfacePackage: String,
    interfaceName: String,
    publicMethods: Sequence<KSFunctionDeclaration>,
): FileSpec = FileSpec
    .builder(interfacePackage, interfaceName)
    .addType(buildInterface(interfaceName, publicMethods))
    .build()
```


```
private fun buildInterface(
    interfaceName: String,
    publicMethods: Sequence<KSFunctionDeclaration>,
): TypeSpec = TypeSpec
    .interfaceBuilder(interfaceName)
    .addFunctions(
        publicMethods
            .map(::buildInterfaceMethod).toList()
    )
    .build()
```


```
private fun buildInterfaceMethod(
    function: KSFunctionDeclaration,
): FunSpec = FunSpec
    .builder(function.simpleName.getShortName())
    .addModifiers(buildFunctionModifiers(function.modifiers))
    .addParameters(
        function.parameters.map(::buildInterfaceMethodParameter)
    )
    .returns(function.returnType!!.toTypeName())
    .addAnnotations(
        function.annotations
            .map { it.toAnnotationSpec() }
            .toList()
    )
    .build()
```


```
private fun buildInterfaceMethodParameter(
    variableElement: KSValueParameter,
): ParameterSpec = ParameterSpec
    .builder(
        variableElement.name!!.getShortName(),
        variableElement.type.toTypeName(),
    )
    .addAnnotations(
        variableElement.annotations
            .map { it.toAnnotationSpec() }.toList()
    )
    .build()
```


```
private fun buildFunctionModifiers(
    modifiers: Set<Modifier>
) = modifiers
    .filterNot { it in IGNORED_MODIFIERS }
    .plus(Modifier.ABSTRACT)
    .mapNotNull { it.toKModifier() }

companion object {
    val IGNORED_MODIFIERS = 
        listOf(Modifier.OPEN, Modifier.OVERRIDE)
}
```


```
private fun assertGeneratedFile(
    sourceFileName: String,
    @Language("kotlin") source: String,
    generatedResultFileName: String,
    @Language("kotlin") generatedSource: String
) {
    val compilation = KotlinCompilation().apply {
        inheritClassPath = true
        kspWithCompilation = true

        sources = listOf(
            SourceFile.kotlin(sourceFileName, source)
        )
        symbolProcessorProviders = listOf(
            GenerateInterfaceProcessorProvider()
        )
    }
    val result = compilation.compile()
    assertEquals(OK, result.exitCode)

    val generated = File(
        compilation.kspSourcesDir,
        "kotlin/$generatedResultFileName"
    )
    assertEquals(
        generatedSource.trimIndent(),
        generated.readText().trimIndent()
    )
}
```


```
class GenerateInterfaceProcessorTest {
    
    @Test
    fun `should generate interface for simple class`() {
        assertGeneratedFile(
            sourceFileName = "RealTestRepository.kt",
            source = """
               import academy.kt.GenerateInterface
  
               @GenerateInterface("TestRepository")
               class RealTestRepository {
                   fun a(i: Int): String = TODO()
                   private fun b() {}
               }
           """,
            generatedResultFileName = "TestRepository.kt",
            generatedSource = """
               import kotlin.Int
               import kotlin.String
              
               public interface TestRepository {
                 public fun a(i: Int): String
               }
           """
        )
    }

    // ...
}
```


```
class GenerateInterfaceProcessorTest {
    // ...
    
    @Test
    fun `should fail when incorrect name`() {
        assertFailsWithMessage(
            sourceFileName = "RealTestRepository.kt",
            source = """
              import academy.kt.GenerateInterface

              @GenerateInterface("")
              class RealTestRepository {
                  fun a(i: Int): String = TODO()
                  private fun b() {}
           }
          """,
            message = "Interface name cannot be empty"
        )
    }
    
    // ...
}
```


```
// A.kt
@GenerateInterface("IA")
class A {
    fun a()
}

// B.kt
@GenerateInterface("IB")
class B {
    fun b()
}
```


```
// A.kt
@GenerateInterface
open class A {
    // ...
}

// B.kt
class B : A() {
    // ...
}
```


```
val dependencies = Dependencies(
    aggregating = false,
    annotatedClass.containingFile!!
)
val file = codeGenerator.createNewFile(
    dependencies,
    packageName,
    fileName
)
```


```
fun classWithParents(
    classDeclaration: KSClassDeclaration
): List<KSClassDeclaration> = classDeclaration
    .superTypes
    .map { it.resolve().declaration }
    .filterIsInstance<KSClassDeclaration>()
    .flatMap { classWithParents(it) }
    .toList()
    .plus(classDeclaration)

val dependencies = Dependencies(
    aggregating = ann.dependsOn.isNotEmpty(),
    *classWithParents(annotatedClass)
        .mapNotNull { it.containingFile }
        .toTypedArray()
)
```


```
val dependencies = Dependencies(
    aggregating = false,
    annotatedClass.containingFile!!
)
val file = codeGenerator.createNewFile(
    dependencies,
    packageName,
    fileName
)
```


```
@Single
class UserRepository {
    // ...
}

@Provide
class UserService(
    val userRepository: UserRepository
) {
    // ...
}
```


```
class UserRepositoryProvider : SingleProvider<UserRepository>() {
    
    private val instance = UserRepository()

    override fun single(): UserRepository = instance
}

class UserServiceProvider : Provider<UserService>() {
    private val userRepositoryProvider = UserRepositoryProvider()

    override fun provide(): UserService =
        UserService(userRepositoryProvider.single())
}
```


```
class ProviderGenerator(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val provideSymbols = resolver.getSymbolsWithAnnotation(
            Provide::class.qualifiedName!!
        )
        val singleSymbols = resolver.getSymbolsWithAnnotation(
            Single::class.qualifiedName!!
        )
        
        val symbols = (singleSymbols + provideSymbols)
            .filterIsInstance<KSClassDeclaration>()
        
        val notProcessed = symbols
            .filterNot(::generateProvider)
        
        return notProcessed.toList()
    }
    
    // ...
}
```