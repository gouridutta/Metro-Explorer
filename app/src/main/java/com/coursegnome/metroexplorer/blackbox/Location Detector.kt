package com.coursegnome.metroexplorer.blackbox

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import com.coursegnome.metroexplorer.R
import android.widget.Toast
import com.coursegnome.metroexplorer.activity.MainActivity



class LocationDetector (context : Context) {

    val mFusedLocationClient: FusedLocationProviderClient
    = LocationServices.getFusedLocationProviderClient(context)

    fun getLocation () {

        mFusedLocationClient.lastLocation
    }

}