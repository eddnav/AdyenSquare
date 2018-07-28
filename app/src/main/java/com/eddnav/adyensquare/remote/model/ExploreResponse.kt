package com.eddnav.adyensquare.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Eduardo Naveda
 */
@JsonClass(generateAdapter = true)
data class ExploreResponse(@Json(name = "response") val content: Content)