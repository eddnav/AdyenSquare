package com.eddnav.adyensquare.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eddnav.adyensquare.R

class MainActivity : AppCompatActivity(), ExploreFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val tx = supportFragmentManager.beginTransaction()
            tx.add(R.id.container, ExploreFragment())
            tx.commit()
        }
    }

    override fun toDetails(venueId: String, venueName: String) {
        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.container, VenueDetailFragment.newInstance(venueId, venueName))
        tx.addToBackStack(null)
        tx.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}