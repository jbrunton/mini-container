package com.jbrunton.inject

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContainerTest {

    lateinit var container: Container

    @Before
    fun setUp() {
        container = Container()
    }

    @Test
    fun resolvesSingletons() {
        container.single{ Foo() }

        val foo1: Foo = container.get()
        val foo2: Foo = container.get()

        assertThat(foo1).isEqualTo(foo2)
    }

    @Test
    fun resolvesWithFactories() {
        container.factory { Foo() }

        val foo1: Foo = container.get()
        val foo2: Foo = container.get()

        assertThat(foo1).isNotEqualTo(foo2)
    }

    @Test(expected = ResolutionFailure::class)
    fun throwsIfCannotResolve() {
        container.get<Foo>()
    }

    @Test
    fun resolvesViaParent() {
        val foo = Foo()
        val bar = Bar()
        container.single { foo }
        val child = container.createChildContainer().apply {
            single { bar }
        }

        assertThat(child.get<Foo>()).isEqualTo(foo)
        assertThat(child.get<Bar>()).isEqualTo(bar)
    }

    @Test
    fun itReturnsIfTypeRegistered() {
        val foo = Foo()
        val bar = Bar()
        container.single { foo }
        val child = container.createChildContainer().apply {
            single { bar }
        }

        assertThat(child.isRegistered(Foo::class)).isEqualTo(true)
        assertThat(child.isRegistered(Bar::class)).isEqualTo(true)
        assertThat(child.isRegistered(Baz::class)).isEqualTo(false)
    }

    @Test(expected = TypeAlreadyRegistered::class)
    fun itThrowsIfSingletonAlreadyRegistered() {
        container.single { Foo() }
        container.single { Foo() }
    }

    @Test(expected = TypeAlreadyRegistered::class)
    fun itThrowsIfFactoryAlreadyRegistered() {
        container.single { Foo() }
        container.single { Foo() }
    }

    @Test
    fun itAllowsSingletonOverrides() {
        val foo1 = Foo()
        val foo2 = Foo()
        container.single { foo1 }

        container.single(override = true) { foo2 }

        assertThat(container.get<Foo>()).isEqualTo(foo2)
    }

    @Test
    fun itAllowsFactoryOverrides() {
        val foo1 = Foo()
        val foo2 = Foo()
        container.factory { foo1 }

        container.factory(override = true) { foo2 }

        assertThat(container.get<Foo>()).isEqualTo(foo2)
    }

    @Test
    fun itValidatesTheContainer() {
        container.apply {
            factory { Foo() }
            factory { Baz(get()) }
        }

        container.dryRun()
    }

    @Test(expected = ResolutionFailure::class)
    fun itFailsValidationIfMissingDefinitions() {
        container.apply {
            factory { Baz(get()) }
        }

        container.dryRun()
    }

    @Test
    fun itValidatesWithParameterLists() {
        container.apply {
            factory { (foo: Foo) -> Baz(foo) }
        }

        container.dryRun {
            paramsFor(Baz::class, parametersOf(Foo()))
        }
    }

    @Test
    fun itLoadsModules() {
        val FooModule = module { single { Foo() } }
        val BarModule = module { single { Baz(get()) } }

        container.register(FooModule, BarModule)

        val baz: Baz = container.get()
        val foo: Foo = container.get()
        assertThat(baz.foo).isEqualTo(foo)
    }
}
