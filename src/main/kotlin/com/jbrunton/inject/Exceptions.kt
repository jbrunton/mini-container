package com.jbrunton.inject

class ResolutionFailure : RuntimeException {
    internal constructor(key: Container.Key) : super(messageFor(key))
    internal constructor(key: Container.Key, cause: ResolutionFailure) : super(messageFor(key, cause), cause)

    companion object {
        internal fun messageFor(key: Container.Key): String =
                "Unable to resolve type $key"

        internal fun messageFor(key: Container.Key, cause: ResolutionFailure): String =
                "${cause.message}, required by $key"
    }
}

class TypeAlreadyRegistered(message: String) : RuntimeException(message) {
    internal constructor(key: Container.Key) : this(messageFor(key))

    companion object {
        internal fun messageFor(key: Container.Key): String =
                "Type $key is already registered in this container, set override = true if intended"
    }
}
