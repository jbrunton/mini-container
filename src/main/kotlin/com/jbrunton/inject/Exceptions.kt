package com.jbrunton.inject

import kotlin.reflect.KClass

class ResolutionFailure(klass: KClass<*>, tag: Any?) : RuntimeException(
        messageFor(klass, tag)
) {
    companion object {
        fun messageFor(klass: KClass<*>, tag: Any?): String {
            return if (tag == null) {
                "Unable to resolve type ${klass.qualifiedName}"
            } else {
                "Unable to resolve type ${klass.qualifiedName} with tag ${tag}"
            }
        }
    }
}

class TypeAlreadyRegistered(klass: KClass<*>, tag: Any?) :
        RuntimeException(messageFor(klass, tag))
{
    companion object {
        fun messageFor(klass: KClass<*>, tag: Any?): String {
            return if (tag == null) {
                "Type ${klass.qualifiedName} is already registered in this container, " +
                        "set override = true if intended"
            } else {
                "Type ${klass.qualifiedName} with tag ${tag} is already registered in this" +
                        "container, set override = true if intended"
            }
        }
    }
}
