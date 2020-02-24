package com.jbrunton.inject

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ExceptionsTest {
    @Test
    fun resolutionFailureMessage() {
        val resolutionFailure = ResolutionFailure(Container.Key(Foo::class, null))
        assertThat(resolutionFailure.message)
                .isEqualTo("Unable to resolve type com.jbrunton.inject.Foo")
    }

    @Test
    fun resolutionFailureWithTagMessage() {
        val resolutionFailure = ResolutionFailure(Container.Key(Foo::class, "my-tag"))
        assertThat(resolutionFailure.message)
                .isEqualTo("Unable to resolve type com.jbrunton.inject.Foo, tag=my-tag")
    }
}
