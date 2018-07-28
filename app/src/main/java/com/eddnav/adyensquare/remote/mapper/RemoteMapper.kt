package com.eddnav.adyensquare.remote.mapper

import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.remote.model.ExploreResponse

/**
 * @author Eduardo Naveda
 */
class RemoteMapper {

    companion object {
        fun toData(model: ExploreResponse): List<Venue> {
            return model.content.groups.flatMap {
                return@flatMap it.items.map {
                    return@map Venue(it.venue.id, it.venue.name, it.venue.location.lat, it.venue.location.lng)
                }
            }
        }
    }
}