package com.jbrunton.inject

class ResolutionFailure(message: String) : RuntimeException(message) {
    internal constructor(key: Container.Key) : this(messageFor(key))

    companion object {
        internal fun messageFor(key: Container.Key): String =
                "Unable to resolve type $key"
    }
}

class TypeAlreadyRegistered(message: String) : RuntimeException(message) {
    internal constructor(key: Container.Key) : this(messageFor(key))

    companion object {
        internal fun messageFor(key: Container.Key): String =
                "Type $key is already registered in this container, set override = true if intended"
    }
}
