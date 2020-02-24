package com.jbrunton.inject

import kotlin.reflect.KClass

/**
 * The dependency injection container. Maintains mappings between types (as `KClass<*>` and `Definition<T>` functions
 * which will resolve to an instance.
 *
 * @param parent the parent container.
 */
class Container(val parent: Container? = null) {
    internal data class Key(val klass: KClass<*>, val tag: Any?)

    private val singletonRegistry = HashMap<Key, Any>()
    private val singletonDefinitions = HashMap<Key, Definition<*>>()
    private val factoryDefinitions = HashMap<Key, Definition<*>>()

    /**
     * Registers a singleton [Definition] for the type `T`. All subsequent calls to [get()] for `T::class` will resolve
     * to the same instance.
     */
    inline fun <reified T : Any> single(
            tag: Any? = null,
            override: Boolean = false,
            noinline definition: Definition<T>
    ) {
        registerSingleton(T::class, tag, override, definition)
    }

    /**
     * Registers a factory [Definition] for the type `T`. All subsequent calls to [get()] for `T::class` will be
     * resolved by invoking `definition`.
     */
    inline fun <reified T : Any> factory(
            tag: Any? = null,
            override: Boolean = false,
            noinline definition: Definition<T>
    ) {
        registerFactory(T::class, tag, override, definition)
    }

    inline fun <reified T : Any> get(tag: Any? = null): T {
        return resolve(T::class, tag)
    }

    fun createChildContainer() = Container(this)

    fun isRegistered(klass: KClass<*>, tag: Any? = null): Boolean {
        val key = Key(klass, tag)
        return singletonDefinitions.containsKey(key)
                || factoryDefinitions.containsKey(key)
                || parent?.isRegistered(klass, tag) ?: false
    }

    fun <T : Any> resolve(klass: KClass<T>, tag: Any? = null, parameters: ParameterDefinition = emptyParameterDefinition()): T {
        return tryResolveSingleton(klass, tag, parameters)
                ?: tryResolveFactory(klass, tag, parameters)
                ?: parent?.resolve(klass, tag, parameters)
                ?: throw ResolutionFailure(klass, tag)
    }

    fun <T : Any> registerSingleton(
            klass: KClass<T>,
            tag: Any? = null,
            override: Boolean = false,
            definition: Definition<T>
    ) {
        checkAndPut(singletonDefinitions, klass, tag, override, definition)
    }

    fun <T : Any> registerFactory(
            klass: KClass<T>,
            tag: Any?,
            override: Boolean = false,
            definition: Definition<T>
    ) {
        checkAndPut(factoryDefinitions, klass, tag, override, definition)
    }

    fun register(vararg modules: Module) {
        for (module in modules) {
            module.registerTypes(this)
        }
    }

    fun dryRun(parameters: DryRunParameters = DryRunParameters()) {
        singletonDefinitions.forEach { key, definition ->
            definition.invoke(parameters.forClass(key))
        }
        factoryDefinitions.forEach { key, definition ->
            definition.invoke(parameters.forClass(key))
        }
        parent?.dryRun(parameters)
    }

    fun dryRun(block: DryRunParameters.() -> Unit) {
        val parameters = DryRunParameters().apply(block)
        dryRun(parameters)
    }

    private fun <T : Any> tryResolveSingleton(klass: KClass<T>, tag: Any?, parameters: ParameterDefinition): T? {
        val key = Key(klass, tag)
        var instance = singletonRegistry.get(key) as T?
        if (instance == null) {
            instance = singletonDefinitions.get(key)?.invoke(parameters()) as T?
            if (instance != null) {
                singletonRegistry.put(key, instance)
            }
        }
        return instance
    }

    private fun <T : Any> tryResolveFactory(klass: KClass<T>, tag: Any?, parameters: ParameterDefinition): T? {
        val key = Key(klass, tag)
        return factoryDefinitions.get(key)?.invoke(parameters()) as T?
    }

    private fun checkAndPut(
            definitions: HashMap<Key, Definition<*>>,
            klass: KClass<*>,
            tag: Any?,
            override: Boolean,
            definition: Definition<*>)
    {
        if (!override && isRegistered(klass, tag)) {
            throw TypeAlreadyRegistered(klass, tag)
        }
        definitions.put(Key(klass, tag), definition)
    }
}

typealias Definition<T> = (ParameterList) -> T
