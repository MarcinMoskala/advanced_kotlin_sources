```
plugins {
    id("kotlin-parcelize")
}
```


```
@file:OptIn(ExperimentalCompilerApi::class)

class AllOpenComponentRegistrar : CompilerPluginRegistrar() {
   override fun ExtensionStorage.registerExtensions(
       configuration: CompilerConfiguration
   ) {
       FirExtensionRegistrarAdapter
           .registerExtension(FirAllOpenExtensionRegistrar())
   }

   override val supportsK2: Boolean
       get() = true
}

class FirAllOpenExtensionRegistrar : FirExtensionRegistrar(){
   override fun ExtensionRegistrarContext.configurePlugin() {
       +::FirAllOpenStatusTransformer
   }
}

class FirAllOpenStatusTransformer(
    session: FirSession
) : FirStatusTransformerExtension(session) {
   override fun needTransformStatus(
       declaration: FirDeclaration
   ): Boolean = declaration is FirRegularClass

   override fun transformStatus(
       status: FirDeclarationStatus,
       declaration: FirDeclaration
   ): FirDeclarationStatus =
       status.transform(modality = Modality.OPEN)
}
```


```
class FirScriptSamWithReceiverConventionTransformer(
    session: FirSession
) : FirSamConversionTransformerExtension(session) {
    override fun getCustomFunctionTypeForSamConversion(
        function: FirSimpleFunction
    ): ConeLookupTagBasedType? {
        val containingClassSymbol = function
            .containingClassLookupTag()
            ?.toFirRegularClassSymbol(session)
            ?: return null

        return if (shouldTransform(it)) {
            val parameterTypes = function.valueParameters
                .map { it.returnTypeRef.coneType }
            if (parameterTypes.isEmpty()) return null
            createFunctionType(
                getFunctionType(it),
                parameters = parameterTypes.drop(1),
                receiverType = parameterTypes[0],
                rawReturnType = function.returnTypeRef
                    .coneType
            )
        } else null
    }

    // ...
}
```


```
suspend fun suspendFunction() = ...

fun blockingFunction() = runBlocking { suspendFunction() }
```