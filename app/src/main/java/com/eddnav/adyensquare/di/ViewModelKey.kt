package com.eddnav.adyensquare.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author Eduardo Naveda
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
// Notes on KClass in annotations: https://stackoverflow.com/questions/44638878/binding-into-map-with-kclass-type
annotation class ViewModelKey(val value: KClass<out ViewModel>)