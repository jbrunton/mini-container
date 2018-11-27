package com.jbrunton.inject

import java.lang.IllegalArgumentException

// This file courtesy of Koin: https://github.com/InsertKoinIO/koin

class ParameterList(vararg value: Any?) {

    val values: List<*> = value.toList()

    private fun <T> elementAt(i: Int): T =
            if (values.size > i) values[i] as T else throw IllegalArgumentException("Can't get parameter value #$i from $this")

    operator fun <T> component1(): T = elementAt(0)
    operator fun <T> component2(): T = elementAt(1)
    operator fun <T> component3(): T = elementAt(2)
    operator fun <T> component4(): T = elementAt(3)
    operator fun <T> component5(): T = elementAt(4)

    /**
     * get element at given index
     * return T
     */
    operator fun <T> get(i: Int) = values[i] as T

    /**
     * Get first element of given type T
     * return T
     */
    inline fun <reified T> get() = values.first { it is T }

}

/**
 * Function to define a parameter list
 * @see ParameterList
 */
typealias ParameterDefinition = () -> ParameterList

/**
 * Help loadModules a Parameter list
 * @see ParameterList
 * return ParameterList
 */
fun parametersOf(vararg value: Any?) = ParameterList(*value)

/**
 * Empty Parameter List
 */
fun emptyParameterDefinition() = { ParameterList() }
