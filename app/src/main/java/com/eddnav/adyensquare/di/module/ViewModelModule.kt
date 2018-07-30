package com.eddnav.adyensquare.di.module

import android.arch.lifecycle.ViewModel
import com.eddnav.adyensquare.di.ViewModelKey
import com.eddnav.adyensquare.presentation.ExploreViewModel
import com.eddnav.adyensquare.presentation.VenueDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Eduardo Naveda
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExploreViewModel::class)
    abstract fun exploreViewModel(viewModel: ExploreViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(VenueDetailViewModel::class)
    abstract fun venueDetailViewModel(viewModel: VenueDetailViewModel): ViewModel
}