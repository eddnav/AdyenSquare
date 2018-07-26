package com.eddnav.adyensquare.data.remote

import com.eddnav.adyensquare.data.remote.model.ExploreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Eduardo Naveda
 */
interface FoursquareService {

    @GET("/v2/venues/explore")
    fun trending(@Query("ll") coordinates: String): Single<ExploreResponse>

    companion object {
        const val BASE_URL = "https://api.foursquare.com"
    }
}