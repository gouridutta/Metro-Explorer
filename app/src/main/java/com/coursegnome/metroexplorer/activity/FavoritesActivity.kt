package com.coursegnome.metroexplorer.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.tasks.LandmarksAdapter
import com.coursegnome.metroexplorer.tasks.PersistanceManager
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    private lateinit var persistanceManager: PersistanceManager
    lateinit private var staggeredLayoutManager : StaggeredGridLayoutManager
    lateinit var adapter: LandmarksAdapter


    private val onItemClickListener = object : LandmarksAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        //initialize Persistance Manager
        persistanceManager = PersistanceManager(this)

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        favoriteslist.layoutManager = staggeredLayoutManager

        adapter = LandmarksAdapter(persistanceManager.getSavedFavoriteLandmarks(), this)
        favoriteslist.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }


}
