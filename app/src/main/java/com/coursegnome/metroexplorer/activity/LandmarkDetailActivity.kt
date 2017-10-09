package com.coursegnome.metroexplorer.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.activity_metro_stations.*

class LandmarkDetailActivity : AppCompatActivity() {

    lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Landmark Title");
        setContentView(R.layout.activity_landmark_detail)

    }
}
