package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class Venue(val id: String, val name: String, val location: Location, val contact: Contact?,
                 val likes: Likes?, val rating: Double?, val description: String?, val photos: Photos?)