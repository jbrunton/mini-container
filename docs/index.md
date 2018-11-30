---
layout: default
title: MiniContainer
---

<a href="#what-is-this">What is this?</a> &middot;
<a href="#a-simple-example">A simple example</a> &middot;
<a href="#getting-started">Getting started</a> &middot;
<a href="#documentation">Documentation</a>

## What is this?

`MiniContainer` is a simple Kotlin dependency injection framework with a concise DSL and a straightforward scoping mechanism.

The syntax is heavily inspired by [Koin](https://insert-koin.io/), but with an inheritance-based scoping mechanism instead.

## A simple example

```kotlin
class UserViewModel(val user: User, val repository: HttpRepository) {
    ...
}

val HttpModule = module {
    single { RetrofitService.create() }
    single { HttpRepository(service = get()) }
}

val UiModule = module {
    factory{ (user: User) -> UserViewModel(user, get()) }
}

class MyApp : Application(), HasContainer {
    override val container = Container()
    
    override fun onCreate() {
        super.onCreate()
        container.register(HttpModule, UiModule)
    }
}

class MyActivity : FragmentActivity(), HasContainer {
    val repository: HttpRepository by inject()
    val viewModel: UserViewModel by injectViewModel() // from mini-container-android

    override val container by lazy { (applicationContext as HasContainer).container }
}

```

## Getting started

First make sure you're using [Jitpack](https://jitpack.io/) by adding it as a repository:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the `mini-container` artifact as a dependency to your build file:

```groovy
dependencies {
    implementation 'com.github.jbrunton:mini-container:VERSION' // core injection framework
    implementation 'com.github.jbrunton:mini-container-android:VERSION' // for injecting androidx viewmodels
}
```

## Documentation

For more details see [the docs](dokka/mini-container).