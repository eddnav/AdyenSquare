package com.eddnav.adyensquare.di.component

import com.eddnav.adyensquare.AdyenSquareApplication
import com.eddnav.adyensquare.di.module.ApplicationModule
import com.eddnav.adyensquare.di.module.LocationModule
import com.eddnav.adyensquare.di.module.RemoteModule
import com.eddnav.adyensquare.di.module.ViewModelModule
import com.eddnav.adyensquare.di.scope.Application
import com.eddnav.adyensquare.view.ExploreFragment
import com.eddnav.adyensquare.view.VenueDetailFragment
import dagger.BindsInstance
import dagger.Component

/**
 * @author Eduardo Naveda
 */
@Application
@Component(modules = [ApplicationModule::class, RemoteModule::class, LocationModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: ExploreFragment)

    fun inject(fragment: VenueDetailFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AdyenSquareApplication): Builder

        fun build(): ApplicationComponent
    }
}