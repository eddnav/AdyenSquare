package com.eddnav.adyensquare.remote

import com.eddnav.adyensquare.remote.model.ExploreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Eduardo Naveda
 */
interface FoursquareService {

    @GET("/v2/venues/explore")
    fun explore(@Query("ll") coordinates: String): Single<ExploreResponse>

    companion object {
        const val BASE_URL = "https://api.foursquare.com"
        const val HOST = "api.foursquare.com"

        const val CLIENT_ID_PARAM = "client_id"
        const val CLIENT_SECRET_PARAM = "client_secret"
        const val VERSIONING_PARAM = "v"

        const val CLIENT_ID = "BGOZOCLI24VGS4KHOBKRK2I05IYIOH0QQTD04VYTXZJPZFUV"
        const val CLIENT_SECRET = "TZTP0VTMGPXO1KLPZKBYV0BL4WOBKESDGYG3DV1N5CHA3YHL"
        const val VERSIONING = "20180729" // https://developer.foursquare.com/docs/api/configuration/versioning
    }
}