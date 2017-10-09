package com.coursegnome.metroexplorer.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.coursegnome.metroexplorer.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LandmarksActivity : AppCompatActivity() {

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager

    lateinit var mFusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            val y = mFusedLocationClient.lastLocation
            val x = mFusedLocationClient.lastLocation
        }

        staggeredLayoutManager = StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL)
        val selectedStation :String = intent.getStringExtra("station")
        println(selectedStation)


    }

}
