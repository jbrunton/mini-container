package com.jbrunton.inject

interface HasContainer {
    val container: Container
}

inline fun <reified T: Any> HasContainer.resolve(noinline parameters: ParameterDefinition = emptyParameterDefinition()): T {
    return container.resolve(T::class, parameters)
}

inline fun <reified T: Any> HasContainer.inject(): Lazy<T> = lazy { resolve<T>() }
