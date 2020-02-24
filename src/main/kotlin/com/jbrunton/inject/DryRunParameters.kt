package com.jbrunton.inject

import kotlin.reflect.KClass

class DryRunParameters {
    private val parameterMap = HashMap<Container.Key, ParameterList>()

    fun paramsFor(klass: KClass<*>, parameters: ParameterList) {
        parameterMap.put(Container.Key(klass, null), parameters)
    }

    fun forClass(klass: KClass<*>): ParameterList {
        return parameterMap.get(Container.Key(klass, null)) ?: ParameterList()
    }
}
