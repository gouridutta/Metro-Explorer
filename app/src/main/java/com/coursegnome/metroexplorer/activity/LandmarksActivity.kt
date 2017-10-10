package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.tasks.FetchLandmarksManager
import com.coursegnome.metroexplorer.model.Landmark
import com.coursegnome.metroexplorer.tasks.LandmarksAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_metro_stations.*
import kotlinx.android.synthetic.main.landmark_item.view.*
import kotlinx.android.synthetic.main.station_item.view.*

class LandmarksActivity : AppCompatActivity(), FetchLandmarksManager.YelpSearchCompletedListener {

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    lateinit var landmarksManager : FetchLandmarksManager
    lateinit var adapter: LandmarksAdapter

    var stationName : String? = null

    private val onItemClickListener = object : LandmarksAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent (this@LandmarksActivity, LandmarkDetailActivity::class.java)
            intent.putExtra("stationName", stationName)
            intent.putExtra("landmarkName",view.landmarkName.getTag(R.id.LANDMARK_NAME_TAG) as String )
            intent.putExtra("address",view.landmarkName.getTag(R.id.ADDRESS_TAG) as String )
            intent.putExtra("distance",view.landmarkName.getTag(R.id.DISTANCE_TAG) as Float)
            intent.putExtra("phone",view.landmarkName.getTag(R.id.PHONE_TAG) as String)
            intent.putExtra("yelp_url",view.landmarkName.getTag(R.id.YELP_URL_TAG) as String)
            intent.putExtra("image_url",view.landmarkName.getTag(R.id.IMAGE_URL_TAG) as String)
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

        val lat = intent.getFloatExtra("lat", 0.0f)
        val lon = intent.getFloatExtra("lon", 0.0f)
        stationName =  intent.getStringExtra("stationName")

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
