package com.coursegnome.metroexplorer.blackbox

import android.app.Activity
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



class LocationDetector {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    val REQUEST_CODE_ASK_PERMISSIONS = 123

    fun getLocation(activityParam: Activity) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activityParam)

        val hasWriteContactsPermission = ContextCompat.checkSelfPermission(activityParam, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activityParam, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),REQUEST_CODE_ASK_PERMISSIONS)
        }

    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Log.d("Hi", "worked")
            } else {
                Log.d("No", "Didnt work")
            }
        }
    }
}