package com.eddnav.adyensquare.di.module

import android.arch.lifecycle.ViewModel
import com.eddnav.adyensquare.di.ViewModelKey
import com.eddnav.adyensquare.presentation.ExploreViewModel
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

}