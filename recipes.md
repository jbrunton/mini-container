---
layout: default
title: Recipes
---

# Recipes

More usage examples. See [What is this?](https://jbrunton.github.io/mini-container/) for more context and to get started.

## Registering types

```kotlin
val module = {
    single { Foo() }
    factory { Bar(foo = get()) }
    factory { (foo: Foo) -> Baz(foo) }
}

val container = Container()
container.register(module)

val foo = container.get<Foo>()
val bar: Bar = container.get()
val baz: Baz = container.resolve { parametersOf(foo) }

```

## Overriding types

```kotlin
val module = {
    single { RetrofitService.create() }
}

val container = Container()
container.register(module)

// For example, for testing purposes
container.singleton(override = true) { mock(RetrofitService::class) }
```

## Scopes

```kotlin
fun UserModule(user: User) = module {
    single { user }
    factory { UserViewModel(user = get()) }
}

// Once we have our user create a new child container. The scope of
// this module is then simply the scope of userContainer.
val userContainer = container.createChildContainer().apply {
    register(UserModule(user))
}
```

## Keeping modules tidy for testing

```kotlin
open class AppModule : Module {
    override fun registerTypes(container: Container) {
        container.register(
                schedulersModule(),
                httpModule(),
                uiModule()
        )
    }

    open fun schedulersModule(): Module = SchedulersModule
    open fun httpModule(): Module = HttpModule
    open fun uiModule(): Module = UiModule
}

class TestAppModule : AppModule() {
    override fun schedulersModule() = TestSchedulersModule
    override fun httpModule() = TestHttpModule
}
```

## Testing completeness of the container or modules

```kotlin
val module1 = module { single { Foo() } }
val module2 = module { factory { (foo: Foo) -> Bar(foo) } }
val container = container().apply { register(module1, module2) }

container.dryRun()
module1.check()
module2.check {
    // dummy params to pass to the factory definition
    paramsFor(Bar::class, parametersOf(Foo()))
}
```

## Documentation

For more details see [the docs](dokka/mini-container). See [What is this?](https://jbrunton.github.io/mini-container/) for more context and to get started.

