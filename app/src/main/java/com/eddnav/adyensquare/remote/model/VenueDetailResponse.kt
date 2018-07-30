package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class VenueDetailResponse(@Json(name = "response") val content: Content) {

    /**
     * TODO: might be able to improve this using custom adapters.
     */
    @JsonClass(generateAdapter = true)
    data class Content(val venue: Venue)
}