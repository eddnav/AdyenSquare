package com.eddnav.adyensquare.remote.mapper

import com.eddnav.adyensquare.data.model.Bounds
import com.eddnav.adyensquare.data.model.Exploration
import com.eddnav.adyensquare.data.model.LatLng
import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.remote.model.ExploreResponse
import com.eddnav.adyensquare.remote.model.VenueDetailResponse

/**
 * @author Eduardo Naveda
 */
class RemoteMapper {

    companion object {
        fun toData(model: ExploreResponse): Exploration {
            val suggestedBounds = model.content.suggestedBounds
            return Exploration(
                    Bounds(LatLng(suggestedBounds.ne.lat, suggestedBounds.ne.lng), LatLng(suggestedBounds.sw.lat, suggestedBounds.sw.lng)),
                    model.content.groups.flatMap {
                        return@flatMap it.items.map {
                            return@map Venue(it.venue.id, it.venue.name, LatLng(it.venue.location.lat, it.venue.location.lng), it.venue.location.address,
                                    null, null, null, null, null)
                        }
                    })
        }

        fun toData(model: VenueDetailResponse): Venue {
            val venue = model.content.venue
            val photos = venue.photos?.groups?.flatMap {
                return@flatMap it.items.map {
                    return@map "${it.prefix}500x500${it.suffix}"
                }
            }
            return Venue(venue.id, venue.name, LatLng(venue.location.lat, venue.location.lng), venue.location.address,
                    photos, venue.description, venue.likes?.count, venue.rating, venue.contact?.formattedPhone)
        }
    }
}

/**
val name: String, val latLng: LatLng, val address: String?,
val photo: String?, val description: String?, val likes: Int?, val rating: Double?,
val phone: String?, val twitter: String?, val instagram: String?)**/