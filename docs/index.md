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
class UserViewModel(val userId: String, val repository: HttpRepository) {
    //...
}

val HttpModule = module {
    single { RetrofitService.create() }
    single { HttpRepository(service = get()) }
}

val UiModule = module {
    factory{ (userId: String) -> UserViewModel(userId, get()) }
}

class MyApp : Application(), HasContainer {
    override val container = Container()
    
    override fun onCreate() {
        super.onCreate()
        container.register(HttpModule, UiModule)
    }
}

class UserProfileActivity : FragmentActivity(), HasContainer {
    val repository: HttpRepository by inject()
    val viewModel: UserViewModel by injectViewModel { parametersOf(userId) }

    override val container by lazy {
        (applicationContext as HasContainer).container
    }
    
    private val userId get() = intent.extras["USER_ID"] as String
}

```

## Getting started

First make sure you're using [Jitpack](https://jitpack.io/) by adding it as a repository:

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the `mini-container` artifact as a dependency to your build file:

```groovy
dependencies {
    // core injection framework
    implementation 'com.github.jbrunton:mini-container:VERSION'
    // for injecting androidx viewmodels
    implementation 'com.github.jbrunton:mini-container-android:VERSION'
}
```

## Documentation

For more examples see [Recipes](recipes). For more details see [the docs](dokka/mini-container).
