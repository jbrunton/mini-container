package com.jbrunton.inject

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class HasContainerTest {

    lateinit var testContainer: Container
    val foo = Foo()

    @Before
    fun setUp() {
        testContainer = Container()
        testContainer.single { foo }
        testContainer.factory { (foo: Foo) -> Baz(foo) }
    }

    @Test
    fun testInject() {
        val subject = object : HasContainer {
            override val container: Container = testContainer
            val foo: Foo by inject()
        }
        assertThat(subject.foo).isEqualTo(foo)
    }

    @Test
    fun testInjectWithParams() {
        val subject = object : HasContainer {
            override val container: Container = testContainer
            val baz: Baz by inject { parametersOf(foo) }
        }
        assertThat(subject.baz.foo).isEqualTo(foo)
    }
}
