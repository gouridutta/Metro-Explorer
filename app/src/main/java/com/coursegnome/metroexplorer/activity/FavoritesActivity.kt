package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.model.Landmark
import com.coursegnome.metroexplorer.tasks.FetchLandmarksManager
import com.coursegnome.metroexplorer.tasks.LandmarksAdapter
import com.coursegnome.metroexplorer.tasks.PersistanceManager
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.landmark_item.view.*

class FavoritesActivity : AppCompatActivity() {

    private lateinit var persistanceManager: PersistanceManager
    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager
    lateinit var adapter: LandmarksAdapter
    lateinit var landmarksManager : FetchLandmarksManager


    private val onItemClickListener = object : LandmarksAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent (this@FavoritesActivity, LandmarkDetailActivity::class.java)
            intent.putExtra("stationName", "")
            intent.putExtra("landmarkName",view.landmarkName.getTag(R.id.LANDMARK_NAME_TAG) as String )
            intent.putExtra("address",view.landmarkName.getTag(R.id.ADDRESS_TAG) as String )
            intent.putExtra("distance",view.landmarkName.getTag(R.id.DISTANCE_TAG) as Float)
            intent.putExtra("phone",view.landmarkName.getTag(R.id.PHONE_TAG) as String)
            intent.putExtra("yelp_url",view.landmarkName.getTag(R.id.YELP_URL_TAG) as String)
            intent.putExtra("image_url",view.landmarkName.getTag(R.id.IMAGE_URL_TAG) as String)
            intent.putExtra("landmarkObject", Landmark("", "", "", 0.0f , 0.0f, Landmark.Location("","","","","",""),
                    "", "", 0.0f))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        //initialize Persistance Manager
        persistanceManager = PersistanceManager(this)

        landmarksManager = FetchLandmarksManager(this)

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        favoriteslist.layoutManager = staggeredLayoutManager

        adapter = LandmarksAdapter(persistanceManager.getSavedFavoriteLandmarks(), this)
        favoriteslist.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }


}
