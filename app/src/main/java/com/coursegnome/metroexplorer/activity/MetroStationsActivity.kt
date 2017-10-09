package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.MetroStationsAdapter
import kotlinx.android.synthetic.main.activity_metro_stations.*
import kotlinx.android.synthetic.main.list_item.view.*

class MetroStationsActivity : AppCompatActivity() {

    lateinit var adapter: MetroStationsAdapter
    lateinit var staggeredLayoutManager : StaggeredGridLayoutManager

    private val onItemClickListener = object : MetroStationsAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent (this@MetroStationsActivity, LandmarksActivity::class.java)
            intent.putExtra("station", view.placeName.text);
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Select Metro Station");
        setContentView(R.layout.activity_metro_stations)

        staggeredLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager

        adapter = MetroStationsAdapter(this)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }

    fun onCreateView () {
    }

}
