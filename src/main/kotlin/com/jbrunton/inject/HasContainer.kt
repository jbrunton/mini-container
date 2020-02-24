package com.jbrunton.inject

interface HasContainer {
    val container: Container
}

inline fun <reified T: Any> HasContainer.resolve(
        tag: Any? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): T {
    return container.resolve(T::class, tag, parameters)
}

inline fun <reified T: Any> HasContainer.inject(tag: Any? = null): Lazy<T> = lazy { resolve<T>(tag) }

inline fun <reified T: Any> HasContainer.inject(
        tag: Any? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): Lazy<T> = lazy { container.resolve(T::class, tag, parameters) }
