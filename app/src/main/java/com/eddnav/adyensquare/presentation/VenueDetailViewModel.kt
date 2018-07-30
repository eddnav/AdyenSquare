package com.eddnav.adyensquare.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.eddnav.adyensquare.data.FoursquareRepository
import com.eddnav.adyensquare.data.model.Venue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * @author Eduardo Naveda
 */
class VenueDetailViewModel @Inject constructor(private val foursquareRepository: FoursquareRepository) : ViewModel() {

    val venue: MutableLiveData<Venue> = MutableLiveData()

    private var disposable = CompositeDisposable()

    fun fetch(id: String) {
        disposable.add(foursquareRepository.getVenue(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    venue.value = it
                },
                {
                    println(it)
                    // TODO: Log this.
                })
        )
    }
}
