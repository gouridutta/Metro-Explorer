package com.coursegnome.metroexplorer.activity

import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.FetchLandmarksManager
import com.coursegnome.metroexplorer.blackbox.FetchMetroStationsManager
import com.coursegnome.metroexplorer.blackbox.LocationDetector


class MainActivity : AppCompatActivity() {

    val REQUEST_CODE_ASK_PERMISSIONS = 123

    fun getLocation(activityParam: Activity) {
        val hasWriteContactsPermission = ContextCompat.checkSelfPermission(activityParam, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activityParam, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_ASK_PERMISSIONS)
        } else {
            val intent = Intent (this@MainActivity, LandmarksActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                val intent = Intent (this@MainActivity, LandmarksActivity::class.java)
                intent.putExtra("Parent", "Favorites")
                startActivity(intent)
            } else {
                // TODO tell user they can't use this feature without enabling location
                Log.d("No", "Didnt grant permissions")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Metro Explorer");

        val FetchMetroStationsManager = FetchMetroStationsManager(this)
        val FetchLandmarksManager = FetchLandmarksManager (this)

        // load WMTA data if app is being opened for the first time
        FetchMetroStationsManager.downloadStationData()

        closest_station_button.setOnClickListener {
            getLocation(this)
        }

        select_station_button.setOnClickListener {
            val intent = Intent (this@MainActivity, MetroStationsActivity::class.java)
            startActivity(intent)
        }

        favorites_button.setOnClickListener() {
            val intent = Intent (this@MainActivity, LandmarksActivity::class.java)
            startActivity(intent)
        }

    }
}
