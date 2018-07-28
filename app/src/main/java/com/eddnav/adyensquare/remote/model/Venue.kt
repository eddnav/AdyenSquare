package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class Venue(val id: String, val name: String, val location: Location)