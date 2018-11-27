package com.jbrunton.inject

import kotlin.reflect.KClass

class ResolutionFailure(klass: KClass<*>): RuntimeException(
        "Unable to resolve type ${klass.qualifiedName}"
)

class TypeAlreadyRegistered(klass: KClass<*>): RuntimeException(
        "Type ${klass.qualifiedName} is already registered in this container, " +
                "set override = true if intended"
)
