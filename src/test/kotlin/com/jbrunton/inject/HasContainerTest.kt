package com.jbrunton.inject

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class HasContainerTest {

    lateinit var testContainer: Container
    val foo = Foo("foo")

    @Before
    fun setUp() {
        testContainer = Container()
        testContainer.single { foo }
        testContainer.single(tag = "bar") { Foo("bar") }
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

    @Test
    fun testInjectWithTags() {
        val subject = object : HasContainer {
            override val container: Container = testContainer
            val foo: Foo by inject()
            val fooBar: Foo by inject(tag = "bar")
        }
        assertThat(subject.foo.name).isEqualTo("foo")
        assertThat(subject.fooBar.name).isEqualTo("bar")
    }
}
