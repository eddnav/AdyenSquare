package com.eddnav.adyensquare.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import com.eddnav.adyensquare.data.FoursquareRepository
import com.eddnav.adyensquare.data.model.Exploration
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * @author Eduardo Naveda
 */
class ExploreViewModel @Inject constructor(private val foursquareRepository: FoursquareRepository, private val rxLocation: RxLocation) : ViewModel() {

    val venues: MutableLiveData<Exploration> = MutableLiveData()

    private var disposable = CompositeDisposable()

    private val locationRequest: LocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(60000 * 5)

    fun startExploring() {
        try {
            disposable.add(rxLocation.location().updates(locationRequest)
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
        disposable.add(foursquareRepository.getExploration(location.latitude, location.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    venues.value = it
                }, {
                    println(it)
                    // TODO: Log this.
                }))
    }

    fun stopExploring() {
        disposable.clear()
    }
}
