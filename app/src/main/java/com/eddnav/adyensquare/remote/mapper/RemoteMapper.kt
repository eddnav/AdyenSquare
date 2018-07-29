package com.eddnav.adyensquare.remote.mapper

import com.eddnav.adyensquare.data.model.Bounds
import com.eddnav.adyensquare.data.model.Exploration
import com.eddnav.adyensquare.data.model.LatLng
import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.remote.model.ExploreResponse

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
                            return@map Venue(it.venue.id, it.venue.name, LatLng(it.venue.location.lat, it.venue.location.lng))
                        }
                    })
        }
    }
}