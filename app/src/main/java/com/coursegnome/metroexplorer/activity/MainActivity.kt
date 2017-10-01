package com.coursegnome.metroexplorer.activity

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
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

    val MyPREFEFENCES = "MyPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FetchMetroStationsManager = FetchMetroStationsManager(this)

        // load WMTA data if app is being opened for the first time
        FetchMetroStationsManager.downloadStationData()

        closest_station_button.setOnClickListener {
            LocationDetector = LocationDetector()
            LocationDetector.getLocation(this)
        }

        select_station_button.setOnClickListener {
        }

    }
}
