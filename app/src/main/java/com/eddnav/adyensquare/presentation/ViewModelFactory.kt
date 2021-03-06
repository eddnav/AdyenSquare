package com.eddnav.adyensquare.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.eddnav.adyensquare.di.scope.Application
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Eduardo Naveda
 */
@Application
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}