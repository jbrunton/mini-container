package com.jbrunton.inject.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jbrunton.inject.Container
import com.jbrunton.inject.HasContainer
import com.jbrunton.inject.ParameterDefinition
import com.jbrunton.inject.emptyParameterDefinition
import kotlin.reflect.KClass

fun <T : ViewModel> Container.resolveViewModel(
        activity: FragmentActivity,
        klass: KClass<T>,
        parameters: ParameterDefinition = emptyParameterDefinition()
): T {
    return ViewModelProviders.of(activity, object : ViewModelProvider.Factory {
        override fun <S : ViewModel> create(modelClass: Class<S>): S {
            return resolve(klass, parameters) as S
        }
    }).get(klass.java)
}

fun <T : ViewModel> Container.resolveViewModel(
        fragment: Fragment,
        klass: KClass<T>,
        parameters: ParameterDefinition = emptyParameterDefinition()
): T {
    return ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {
        override fun <S : ViewModel> create(modelClass: Class<S>): S {
            return resolve(klass, parameters) as S
        }
    }).get(klass.java)
}

inline fun <reified T: ViewModel, S : HasContainer> S.injectViewModel(
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): Lazy<T> where S: FragmentActivity =
        lazy { container.resolveViewModel(this, T::class, parameters) }

inline fun <reified T: ViewModel, S : HasContainer> S.injectViewModel(
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): Lazy<T> where S: Fragment =
        lazy { container.resolveViewModel(this, T::class, parameters) }