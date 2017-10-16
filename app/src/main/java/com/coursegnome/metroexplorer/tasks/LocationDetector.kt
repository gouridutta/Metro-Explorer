package com.coursegnome.metroexplorer.tasks

import android.content.Context
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.*
import kotlin.concurrent.timerTask

class LocationDetector(val context: Context) {

    val fusedLocationClient: FusedLocationProviderClient
    var locationListener: LocationListener? = null


    init {
        fusedLocationClient = FusedLocationProviderClient(context)
    }

    interface LocationListener {
        fun locationFound(location: Location)
        fun locationNotFound(reason : String)
    }

    fun detectLocation() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 0L

        val permissionResult = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionResult == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            val timer = Timer()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    fusedLocationClient.removeLocationUpdates(this)
                    timer.cancel()
                    locationListener?.locationFound(locationResult.locations.first())
                }
            }

            timer.schedule(timerTask {
                fusedLocationClient.removeLocationUpdates(locationCallback)
                locationListener?.locationNotFound("Timeout")
            }, 10*1000) //10 seconds

            fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, null)
        }
        else {
            locationListener?.locationNotFound("No permission given")
        }
    }
}