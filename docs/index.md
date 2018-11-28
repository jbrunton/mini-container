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

## A simple example

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
    implementation 'com.github.jbrunton:mini-container:VERSION'
}
```

## Documentation

For more details see [the docs](dokka/mini-container).