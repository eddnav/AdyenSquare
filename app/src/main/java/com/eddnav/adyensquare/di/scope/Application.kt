package com.eddnav.adyensquare.di.scope

import javax.inject.Scope


/**
 * I don't like @Singleton.
 *
 * @author Eduardo Naveda
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Application