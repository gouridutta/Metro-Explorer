package com.coursegnome.metroexplorer.activity

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.FetchMetroStationsManager
import com.coursegnome.metroexplorer.blackbox.LocationDetector


class MainActivity : AppCompatActivity() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    lateinit var LocationDetector : LocationDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        closest_station_button.setOnClickListener {
            LocationDetector = LocationDetector()
            LocationDetector.getLocation(this)
        }

        select_station_button.setOnClickListener {
            val FetchMetroStationsManager = FetchMetroStationsManager(this)
            Log.d("Log works","i hope")
            FetchMetroStationsManager.loadStationData()
        }

    }
}
