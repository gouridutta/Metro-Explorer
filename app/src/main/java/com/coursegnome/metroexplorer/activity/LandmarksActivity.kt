package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.FetchLandmarksManager
import com.coursegnome.metroexplorer.blackbox.Landmark
import com.coursegnome.metroexplorer.blackbox.LandmarksAdapter
import com.coursegnome.metroexplorer.blackbox.MetroStationsAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_metro_stations.*
import kotlinx.android.synthetic.main.station_item.view.*

class LandmarksActivity : AppCompatActivity(), FetchLandmarksManager.YelpSearchCompletedListener {

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    lateinit var landmarksManager : FetchLandmarksManager
    lateinit var adapter: LandmarksAdapter

    private val onItemClickListener = object : LandmarksAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent (this@LandmarksActivity, LandmarkDetailActivity::class.java)
            intent.putExtra("name", view.placeName.text);
            val lat = view.placeName.getTag(R.id.LAT_TAG) as Float
            val lon = view.placeName.getTag(R.id.LON_TAG) as Float
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager

        landmarksManager = FetchLandmarksManager(this)
        landmarksManager.yelpCompleted = this

        val selectedStation = intent.getStringExtra("name")
        val lat = intent.getFloatExtra("lat", 0.0f)
        val lon = intent.getFloatExtra("lon", 0.0f)

        landmarksManager.fetchLandmarks(lat, lon)

    }

    override fun landmarksLoaded(landmarks: ArrayList<Landmark>) {
        val x = landmarks.get(0)
        val y = 2
        adapter = LandmarksAdapter(landmarks, this)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)
    }

}
