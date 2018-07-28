package com.eddnav.adyensquare.di.module

import com.eddnav.adyensquare.AdyenSquareApplication
import com.eddnav.adyensquare.di.scope.Application
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides

/**
 * @author Eduardo Naveda
 */
@Module
class LocationModule {

    @Application
    @Provides
    fun rxLocation(application: AdyenSquareApplication): RxLocation {
        return RxLocation(application)
    }
}