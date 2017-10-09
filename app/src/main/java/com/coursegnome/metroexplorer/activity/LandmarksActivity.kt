package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.LocationDetector
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LandmarksActivity : AppCompatActivity() {

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager

    lateinit var mFusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        staggeredLayoutManager = StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL)

    }
}
