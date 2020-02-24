package com.jbrunton.inject

interface HasContainer {
    val container: Container
}

/**
 * Resolves an instance of the given type.
 *
 * Examples:
 *
 *     class MovieStore(override val container: Container) : HasContainer {
 *         fun example() {
 *             val checkout = resolve<Checkout>()
 *             val comedy: Genre = resolve(tag = "comedy")
 *             val starWars: Movie = resolve { parametersOf(starWarsId) }
 *         }
 *     }
 *
 * @param tag An optional tag.
 * @param parameters Optional parameters to pass to the factory method.
 */
inline fun <reified T: Any> HasContainer.resolve(
        tag: Any? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): T {
    return container.resolve(T::class, tag, parameters)
}

/**
 * Creates a property that lazily resolves instances of the given type.
 *
 * Examples:
 *
 *     class MovieStore(override val container: Container) : HasContainer {
 *         val checkout: Checkout by inject()
 *         val comedy: Genre by inject(tag = "comedy")
 *         val starWars: Movie by inject { parametersOf(starWarsId) }
 *     }
 *
 * @param tag An optional tag.
 * @param parameters Optional parameters to pass to the factory method.
 */
inline fun <reified T: Any> HasContainer.inject(
        tag: Any? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): Lazy<T> = lazy { container.resolve(T::class, tag, parameters) }
