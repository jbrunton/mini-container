package com.jbrunton.inject

class Foo(val name: String? = null) {
    override fun toString(): String {
        return super.toString() + " (name=${name})"
    }
}

class Bar

class Baz(val foo: Foo? = null)

data class Tag(val name: String)
