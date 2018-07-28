package com.eddnav.adyensquare.view

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.eddnav.adyensquare.AdyenSquareApplication
import com.eddnav.adyensquare.R
import com.eddnav.adyensquare.data.model.Venue
import com.eddnav.adyensquare.presentation.ExploreViewModel
import com.eddnav.adyensquare.presentation.ViewModelFactory
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: ExploreViewModel

    private val venues = ArrayList<Venue>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AdyenSquareApplication).applicationComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExploreViewModel::class.java)

        setContentView(R.layout.activity_main)

        viewModel.venues.observe(this, Observer {
            venues.clear()
            if (it != null) {
                venues.addAll(it)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        explore()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopExploring()
    }

    @AfterPermissionGranted(EXPLORE_PERMISSIONS)
    private fun explore() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            viewModel.startExploring()
            Toast.makeText(this, R.string.exploring_toast, Toast.LENGTH_SHORT).show()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale), EXPLORE_PERMISSIONS, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    companion object {
        private const val EXPLORE_PERMISSIONS = 0
    }
}