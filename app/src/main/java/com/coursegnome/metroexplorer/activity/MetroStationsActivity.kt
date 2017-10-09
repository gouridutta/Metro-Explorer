package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
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

        val onItemClickListener = object : MetroStationsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
//                val intent = Intent (this@MetroStationsActivity, LandmarksActivity::class.java)
//                startActivity(intent)
            }
        }

        adapter.setOnItemClickListener(onItemClickListener)

    }

    fun onCreateView () {
    }

}
