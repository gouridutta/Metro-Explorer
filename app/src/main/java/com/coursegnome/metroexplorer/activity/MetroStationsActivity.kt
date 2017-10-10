package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.tasks.FetchMetroStationsManager
import com.coursegnome.metroexplorer.tasks.MetroStationsAdapter
import kotlinx.android.synthetic.main.activity_metro_stations.*
import kotlinx.android.synthetic.main.station_item.view.*

class MetroStationsActivity : AppCompatActivity() {

    lateinit var adapter: MetroStationsAdapter
    lateinit var FetchMetroStationsManager : FetchMetroStationsManager
    lateinit var staggeredLayoutManager : StaggeredGridLayoutManager

    private val onItemClickListener = object : MetroStationsAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent (this@MetroStationsActivity, LandmarksActivity::class.java)
            val lat = view.placeName.getTag(R.id.LAT_TAG) as Float
            val lon = view.placeName.getTag(R.id.LON_TAG) as Float
            val x =  view.placeName.text
            intent.putExtra("stationName", view.placeName.text)
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTitle("Select Metro Station");
        setContentView(R.layout.activity_metro_stations)

        staggeredLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager

        FetchMetroStationsManager = FetchMetroStationsManager(this)
        val stationData = FetchMetroStationsManager.getAllStations()

        adapter = MetroStationsAdapter(stationData)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }

}
