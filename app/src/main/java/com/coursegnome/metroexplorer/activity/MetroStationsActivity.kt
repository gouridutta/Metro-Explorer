package com.coursegnome.metroexplorer.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.blackbox.MetroStationsAdapter
import kotlinx.android.synthetic.main.activity_metro_stations.*

class MetroStationsActivity : AppCompatActivity() {

    lateinit var adapter: MetroStationsAdapter
    lateinit var staggeredLayoutManager : StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("Select Metro Station");

        setContentView(R.layout.activity_metro_stations)

        staggeredLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager

        adapter = MetroStationsAdapter(this)
        list.adapter = adapter

    }

    fun onCreateView () {
    }

}
