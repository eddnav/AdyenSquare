package com.eddnav.adyensquare.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import com.eddnav.adyensquare.data.FoursquareRepository
import com.eddnav.adyensquare.data.model.Venue
import com.patloew.rxlocation.RxLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Eduardo Naveda
 */
class ExploreViewModel @Inject constructor(private val foursquareRepository: FoursquareRepository, private val rxLocation: RxLocation) : ViewModel() {

    val venues: MutableLiveData<List<Venue>> = MutableLiveData()

    private var disposable = CompositeDisposable()

    fun startExploring() {
        try {
            disposable.add(rxLocation.location().lastLocation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        getVenues(it)
                    })
        } catch (e: SecurityException) {
            println(e)
            // TODO: Log this incorrect usage properly.
        }
    }

    private fun getVenues(location: Location) {
        disposable.add(foursquareRepository.getVenuesNear(location.latitude, location.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    venues.value = it
                }, {
                    println(it)
                    // TODO: Show an error, possibly a toast.
                }))
    }

    fun stopExploring() {
        disposable.clear()
    }

}
