package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationDetector (context : Context) {

    val mFusedLocationClient: FusedLocationProviderClient
    = LocationServices.getFusedLocationProviderClient(context)

    fun getLocation () {

//        mFusedLocationClient.lastLocation
    }

}