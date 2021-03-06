package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class ExploreResponse(@Json(name = "response") val content: Content) {

    /**
     * TODO: might be able to improve this using custom adapters.
     */

    @JsonClass(generateAdapter = true)
    data class Content(val suggestedBounds: Bounds, val groups: List<Group>)

    @JsonClass(generateAdapter = true)
    data class Group(val items: List<Item>)

    @JsonClass(generateAdapter = true)
    data class Item(val venue: Venue)
}

