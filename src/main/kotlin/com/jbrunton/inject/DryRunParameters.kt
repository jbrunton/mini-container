package com.jbrunton.inject

import kotlin.reflect.KClass

class DryRunParameters {
    private val parameterMap = HashMap<KClass<*>, ParameterList>()

    fun paramsFor(klass: KClass<*>, parameters: ParameterList) {
        parameterMap.put(klass, parameters)
    }

    fun forClass(klass: KClass<*>): ParameterList {
        return parameterMap.get(klass) ?: ParameterList()
    }
}
