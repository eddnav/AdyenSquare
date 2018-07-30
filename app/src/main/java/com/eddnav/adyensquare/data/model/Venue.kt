package com.eddnav.adyensquare.data.model

/**
 * @author Eduardo Naveda
 */
data class Venue(val id: String, val name: String, val latLng: LatLng, val address: String?,
                 val photos: List<String>?, val description: String?, val likes: Int?, val rating: Double?,
                 val phone: String?)