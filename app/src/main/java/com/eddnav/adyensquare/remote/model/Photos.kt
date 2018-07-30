package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class Photos(val groups: List<Group>) {

    /**
     * TODO: might be able to improve this using custom adapters.
     */

    @JsonClass(generateAdapter = true)
    data class Group(val items: List<Item>)

    @JsonClass(generateAdapter = true)
    data class Item(val width: Int, val height: Int, val prefix: String, val suffix: String)
}