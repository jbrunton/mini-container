package com.jbrunton.inject

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContainerKeyTest {
    @Test
    fun describesKeys() {
        val key = Container.Key(Foo::class, null)
        assertThat(key.toString()).isEqualTo("com.jbrunton.inject.Foo")
    }

    @Test
    fun describesKeysWithTags() {
        val key = Container.Key(Foo::class, "my-tag")
        assertThat(key.toString()).isEqualTo("com.jbrunton.inject.Foo, tag=my-tag")
    }
}
