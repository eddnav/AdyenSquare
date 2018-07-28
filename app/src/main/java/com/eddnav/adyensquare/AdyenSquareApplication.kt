package com.eddnav.adyensquare

import android.app.Application
import com.eddnav.adyensquare.di.component.ApplicationComponent
import com.eddnav.adyensquare.di.component.DaggerApplicationComponent

/**
 * @author Eduardo Naveda
 */
class AdyenSquareApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
    }
}