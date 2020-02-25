package com.jbrunton.inject

import kotlin.reflect.KClass

class DryRunParameters {
    private val parameterMap = HashMap<Container.Key, ParameterList>()

    fun paramsFor(klass: KClass<*>, parameters: ParameterList, tag: Any? = null) {
        parameterMap.put(Container.Key(klass, tag), parameters)
    }

    internal fun forClass(key: Container.Key): ParameterList {
        return parameterMap.get(key) ?: ParameterList()
    }
}
