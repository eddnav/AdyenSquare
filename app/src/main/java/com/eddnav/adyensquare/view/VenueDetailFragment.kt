package com.eddnav.adyensquare.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eddnav.adyensquare.AdyenSquareApplication
import com.eddnav.adyensquare.R
import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.presentation.VenueDetailViewModel
import com.eddnav.adyensquare.presentation.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class VenueDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: VenueDetailViewModel

    private var venueId: String? = null
    private var venueName: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AdyenSquareApplication).applicationComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenueDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            venueId = it.getString(VENUE_ID_PARAM)
            venueName = it.getString(VENUE_NAME_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.title = venueName

        viewModel.venue.observe(this, Observer {
            update(it!!)
        })

        if (savedInstanceState == null) {
            viewModel.fetch(venueId!!)
        }
    }

    private fun update(venue: Venue) {
        if (venue.photos != null && venue.photos.isNotEmpty()) {
            Picasso.get().load(venue.photos[0]).into(photo)
        }

        if (venue.description != null) {
            description.text = venue.description
        } else {
           description.visibility = View.GONE
        }

        address.text = venue.address
        likes.text = venue.likes.toString()

        if (venue.phone != null) {
            phone.text = venue.phone
        } else {
            phone_icon.visibility = View.GONE
            phone.visibility = View.GONE
        }

        rating.text = venue.rating.toString()
    }

    companion object {

        private const val VENUE_ID_PARAM = "venue_id"
        private const val VENUE_NAME_PARAM = "venue_name"

        fun newInstance(venueId: String, venueName: String) =
                VenueDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(VENUE_ID_PARAM, venueId)
                        putString(VENUE_NAME_PARAM, venueName)
                    }
                }
    }
}
