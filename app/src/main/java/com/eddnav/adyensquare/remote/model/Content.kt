package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class Content(val suggestedBounds: Bounds, val groups: List<Groups>)