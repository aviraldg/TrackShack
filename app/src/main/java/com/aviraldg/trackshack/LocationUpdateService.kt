package com.aviraldg.trackshack

import android.app.IntentService
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.parse.ParseGeoPoint
import com.parse.ParseUser

class LocationUpdateService : IntentService("LocationUpdateService"),
    GoogleApiClient.ConnectionCallbacks, LocationListener {
    override fun onLocationChanged(loc: Location) {
        val u = ParseUser.getCurrentUser()
        Log.i("LUS", loc.toString())
        u.put("location", ParseGeoPoint(loc.latitude, loc.longitude))
        u.saveInBackground()
    }

    override fun onConnected(p0: Bundle?) {
        val req = LocationRequest()

        LocationServices.FusedLocationApi.requestLocationUpdates(
                apiClient,
                req,
                this
        )
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    val apiClient = GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .build()

    override fun onHandleIntent(intent: Intent?) {
        apiClient.connect()
    }
}