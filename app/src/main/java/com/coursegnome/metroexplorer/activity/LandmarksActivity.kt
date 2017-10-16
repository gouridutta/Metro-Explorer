package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
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

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager
    lateinit var locationDetector : LocationDetector
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
            intent.putExtra("landmarkObject", landmarksManager.getLandmark(view.landmarkName.getTag(R.id.LANDMARK_NAME_TAG) as String)as Serializable)
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

        val parent = intent.getStringExtra("parent")
        val lat : Float
        val lon : Float

        landmark_toolbar.setTitleTextColor(ContextCompat.getColor(this@LandmarksActivity, R.color.colorWhite))
        setSupportActionBar(landmark_toolbar)
        supportActionBar?.title = ""

        if (parent == "useLocation") {
            locationDetector = LocationDetector(this)
            locationDetector.locationListener = this
            locationDetector.detectLocation()

        } else {
            lat = intent.getFloatExtra("lat", 0.0f)
            lon = intent.getFloatExtra("lon", 0.0f)
            stationName =  intent.getStringExtra("stationName")
            supportActionBar?.title = stationName
            landmarksManager.fetchLandmarks(lat, lon)

        }

    }

    override fun locationFound(location: Location) {
        val userLat = location.latitude
        val userLon = location.longitude

        val fetchStations = FetchMetroStationsManager(this)
        val stations = fetchStations.getAllStations()

        for (i in 0 until stations.size) {
            val latDif = Math.abs(stations[i].lat - userLat)
            val lonDif = Math.abs(stations[i].long - userLon)
            stations[i].distance = latDif + lonDif
        }

        var lowestScore = stations[0]

        for (i in 1 until stations.size) {
            if (stations[i].distance < lowestScore.distance) {
                lowestScore = stations[i]
            }
        }

        supportActionBar?.title = lowestScore.name
        landmarksManager.fetchLandmarks(lowestScore.lat,lowestScore.long)
    }

    override fun locationNotFound(reason: LocationDetector.FailureReason) {
        val and = 2
    }

    override fun landmarksLoaded(landmarks: ArrayList<Landmark>) {
        avi.hide()
        adapter = LandmarksAdapter(landmarks, this)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }


}
