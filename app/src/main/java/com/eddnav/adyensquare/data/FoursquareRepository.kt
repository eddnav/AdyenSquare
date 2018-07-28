package com.eddnav.adyensquare.data

import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.remote.FoursquareService
import com.eddnav.adyensquare.remote.mapper.RemoteMapper
import io.reactivex.Single
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

/**
 * @author Eduardo Naveda
 */
class FoursquareRepository @Inject constructor(private val api: FoursquareService) {

    private var formatter = DecimalFormat("#.#######################")

    init {
        // Force . to be the decimal separator regardless of the locale.
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        formatter.decimalFormatSymbols = symbols
    }


    fun getVenuesNear(lat: Double, lng: Double): Single<List<Venue>> {
        return api.explore("${formatter.format(lat)},${formatter.format(lng)}")
                .map { RemoteMapper.toData(it) }
    }
}