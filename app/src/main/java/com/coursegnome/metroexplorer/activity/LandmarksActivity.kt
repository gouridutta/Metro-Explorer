package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.model.Landmark
import com.coursegnome.metroexplorer.model.StationData
import com.coursegnome.metroexplorer.tasks.FetchLandmarksManager
import com.coursegnome.metroexplorer.tasks.FetchMetroStationsManager
import com.coursegnome.metroexplorer.tasks.LandmarksAdapter
import com.coursegnome.metroexplorer.tasks.LocationDetector
import kotlinx.android.synthetic.main.activity_landmarks.*
import kotlinx.android.synthetic.main.landmark_item.view.*
import java.io.Serializable
import java.util.*

class LandmarksActivity : AppCompatActivity(), FetchLandmarksManager.YelpSearchCompletedListener, LocationDetector.LocationListener {

    lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
    lateinit var locationDetector: LocationDetector
    lateinit var landmarksManager: FetchLandmarksManager
    lateinit var adapter: LandmarksAdapter

    var stationName: String? = null

    private val onItemClickListener = object : LandmarksAdapter.OnItemClickListener {

        /* When landmark clicked, open Landmark Detail Activity and pass all needed info
        that has been saved in Landmark View tags */

        override fun onItemClick(view: View, position: Int) {
            val intent = Intent(this@LandmarksActivity, LandmarkDetailActivity::class.java)
            intent.putExtra("stationName", stationName)
            intent.putExtra("landmarkName", view.landmarkName.getTag(R.id.LANDMARK_NAME_TAG) as String)
            intent.putExtra("address", view.landmarkName.getTag(R.id.ADDRESS_TAG) as String)
            intent.putExtra("distance", view.landmarkName.getTag(R.id.DISTANCE_TAG) as Float)
            intent.putExtra("phone", view.landmarkName.getTag(R.id.PHONE_TAG) as String)
            intent.putExtra("yelp_url", view.landmarkName.getTag(R.id.YELP_URL_TAG) as String)
            intent.putExtra("image_url", view.landmarkName.getTag(R.id.IMAGE_URL_TAG) as String)
            intent.putExtra("landmarkObject", landmarksManager.getLandmark(view.landmarkName.getTag(R.id.LANDMARK_NAME_TAG) as String) as Serializable)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        /* Begin defining layout, layout manager, and toolbar. Toolbar title is blank until we know
        what the parent is */

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager

        landmark_toolbar.setTitleTextColor(ContextCompat.getColor(this@LandmarksActivity, R.color.colorWhite))
        setSupportActionBar(landmark_toolbar)
        supportActionBar?.title = ""

        // Create landmark fetcher, and listener
        landmarksManager = FetchLandmarksManager(this)
        landmarksManager.yelpCompleted = this

        // Get parent activity tag
        val parent = intent.getStringExtra("parent")
        val lat: Float
        val lon: Float

        // If the user wants the closest station, a location detector is created and called
        if (parent == "useLocation") {
            locationDetector = LocationDetector(this)
            locationDetector.locationListener = this
            locationDetector.detectLocation()
        } else {
            /* If the user chose a station, the station data is fetched, and the landmarks manager
            is called with that data */
            lat = intent.getFloatExtra("lat", 0.0f)
            lon = intent.getFloatExtra("lon", 0.0f)
            stationName = intent.getStringExtra("stationName")
            supportActionBar?.title = stationName
            landmarksManager.fetchLandmarks(lat, lon)

        }

    }

    // If location is found
    override fun locationFound(location: Location) {

        // lat and long saved
        val userLat = location.latitude
        val userLon = location.longitude

        // station data detched from JSON
        val fetchStations = FetchMetroStationsManager(this)
        val stations = fetchStations.getAllStations()

        /* each station given distance from user location, by subtracting station lat and long from
        the user's lat and long */
        for (i in 0 until stations.size) {
            val latDif = Math.abs(stations[i].lat - userLat)
            val lonDif = Math.abs(stations[i].long - userLon)
            stations[i].distance = latDif + lonDif
        }

        // the lowest distance is saved
        var lowestScore = stations[0]
        for (i in 1 until stations.size) {
            if (stations[i].distance < lowestScore.distance) {
                lowestScore = stations[i]
            }
        }

        // the station with the lowest distance is used to set the title, and fetch landmarks
        supportActionBar?.title = lowestScore.name
        landmarksManager.fetchLandmarks(lowestScore.lat, lowestScore.long)
    }

    // if location not found
    override fun locationNotFound(reason: String) {
        /* The user is told that their location can't be found (Timeout) or they don't have Location Permission
        The app design should never let the user get this far without permission, but is safety measure */
        val x = Toast.makeText(this, "placeholder", Toast.LENGTH_SHORT)
        if (reason == "Timeout") {
            x.setText(R.string.noLocation)
        } else {
            x.setText(R.string.noPermission)
        }
        x.show()
        // Brought back to the main activity either way
        val intent = Intent(this@LandmarksActivity, MainActivity::class.java)
        startActivity(intent)
    }

    // when landmarks have been loaded by manager
    override fun landmarksLoaded(landmarks: ArrayList<Landmark>) {
        // Loading animation hidden
        avi.hide()

        // Recycler view adapter created based on landmark data
        adapter = LandmarksAdapter(landmarks, this)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }

    // if landmarks can't be loaded
    override fun landmarksNotLoaded() {
        // User told Yelp can't load landmarks, and to check connection
        Toast.makeText(this, R.string.noYelp, Toast.LENGTH_SHORT).show()
        if (intent.getStringExtra("parent") == "useLocation") {
            // sent back to Main if using location
            val intent = Intent(this@LandmarksActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            // sent back to Metro Stations if picked station
            val intent = Intent(this@LandmarksActivity, MetroStationsActivity::class.java)
            startActivity(intent)
        }
    }

}
