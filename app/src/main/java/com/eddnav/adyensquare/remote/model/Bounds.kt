package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class Bounds(val ne: LatLng, val sw: LatLng)