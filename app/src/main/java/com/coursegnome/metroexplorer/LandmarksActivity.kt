package com.coursegnome.metroexplorer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager

class LandmarksActivity : AppCompatActivity() {

    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        staggeredLayoutManager = StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager
//        list.layoutManager = staggeredLayoutManager

    }
}
