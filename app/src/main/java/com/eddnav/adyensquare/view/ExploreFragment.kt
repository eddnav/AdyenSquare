package com.eddnav.adyensquare.view

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eddnav.adyensquare.AdyenSquareApplication
import com.eddnav.adyensquare.R
import com.eddnav.adyensquare.data.model.Exploration
import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.presentation.ExploreViewModel
import com.eddnav.adyensquare.presentation.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_explore.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

/**
 * @author Eduardo Naveda
 */
class ExploreFragment : Fragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks, OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ExploreViewModel

    private var noPermissionsSnackbar: Snackbar? = null

    private var listener: OnFragmentInteractionListener? = null

    private val venueIds: HashMap<Marker, Venue> = HashMap()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            (context.applicationContext as AdyenSquareApplication).applicationComponent.inject(this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExploreViewModel::class.java)
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_explore, container, false)
        val map = layout.findViewById<MapView>(R.id.map)
        map.onCreate(savedInstanceState)

        initialize(map)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        toolbar.title = getString(R.string.app_name)
    }

    override fun onStart() {
        super.onStart()
        map?.onStart()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onPause() {
        super.onPause()
        map?.onPause()
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
        viewModel.stopExploring()
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map?.onSaveInstanceState(outState)

        // TODO: should try to save currently selected marker, will need some shuffling.
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap?) {
        setupMap(map)
        viewModel.venues.observe(this, Observer {
            if (it != null) {
                updateMap(map, it)
            }
        })
        explore()
    }

    private fun setupMap(map: GoogleMap?) {
        try {
            map?.setMinZoomPreference(12.0f)
            map?.isMyLocationEnabled = true
            map?.setOnInfoWindowClickListener {
                listener?.toDetails(venueIds[it]?.id!!, venueIds[it]?.name!!)
            }
        } catch (e: SecurityException) {
            // TODO: Shouldn't ever happen, checked exceptions :/
        }
    }

    /**
     * Checks permissions, if everything is okay, starts map initialization.
     */
    private fun initialize(mapView: MapView) {
        if (EasyPermissions.hasPermissions(activity as Context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mapView.getMapAsync(this)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale), EXPLORE_PERMISSIONS, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun explore() {
        viewModel.startExploring()
        Toast.makeText(activity, R.string.exploring_toast, Toast.LENGTH_SHORT).show()
    }

    private fun updateMap(map: GoogleMap?, exploration: Exploration) {
        val bounds = exploration.bounds
        map?.setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(bounds.sw.lat, bounds.sw.lng), LatLng(bounds.ne.lat, bounds.ne.lng)))
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds(LatLng(bounds.sw.lat, bounds.sw.lng), LatLng(bounds.ne.lat, bounds.ne.lng)), 0))

        // TODO: Do some sort of diff instead, to avoid flickering.
        map?.clear()
        exploration.venues.map {
            venueIds.put(map?.addMarker(createMarkerOptionsForVenue(it))!!, it)
        }
    }

    private fun createMarkerOptionsForVenue(venue: Venue): MarkerOptions {
        return MarkerOptions().apply {
            this.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE
            ))
            this.position(LatLng(venue.latLng.lat, venue.latLng.lng))
            this.title(venue.name)
            if (venue.address != null) {
                this.snippet(venue.address)
            }
        }
    }

    private fun createNoPermissionsSnackbar() {
        // TODO: Might want to do something similar for network status.
        if (noPermissionsSnackbar == null) {
            noPermissionsSnackbar = Snackbar.make(this.view!!, R.string.location_permission_disabled_snackbar, Snackbar.LENGTH_INDEFINITE)
            noPermissionsSnackbar?.setAction(R.string.why_snackbar_action, {
                AppSettingsDialog.Builder(this).build().show()
                noPermissionsSnackbar = null
            })
            noPermissionsSnackbar?.show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        initialize(map)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        createNoPermissionsSnackbar()
    }

    override fun onRationaleAccepted(requestCode: Int) {}

    override fun onRationaleDenied(requestCode: Int) {
        createNoPermissionsSnackbar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && !EasyPermissions.hasPermissions(activity as Context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            createNoPermissionsSnackbar()
        }
    }

    interface OnFragmentInteractionListener {
        fun toDetails(venueId: String, venueName: String)
    }

    companion object {
        private const val EXPLORE_PERMISSIONS = 0
    }
}
