package com.coursegnome.metroexplorer.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.activity_metro_stations.*

class MetroStationsActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("Select Metro Station");

        setContentView(R.layout.activity_metro_stations)

        linearLayoutManager = LinearLayoutManager(this)
        list.layoutManager = linearLayoutManager

    }

    fun onCreateView () {
    }

}
