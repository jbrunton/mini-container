package com.jbrunton.inject

interface Module {
    fun registerTypes(container: Container)
}

fun Module.check(parameters: DryRunParameters = DryRunParameters()) {
    val container = Container()
    container.register(this)
    container.dryRun(parameters)
}

fun Module.check(block: DryRunParameters.() -> Unit) {
    val parameters = DryRunParameters().apply(block)
    check(parameters)
}

fun module(block: Container.() -> Unit): Module {
    return object : Module {
        override fun registerTypes(container: Container) {
            container.apply(block)
        }
    }
}
