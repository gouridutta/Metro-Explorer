package com.coursegnome.metroexplorer.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE_ASK_PERMISSIONS = 123

    fun checkPermissions(activityParam: Activity) {
        val hasWriteContactsPermission = ContextCompat.checkSelfPermission(activityParam, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if user has not granted them, or removed them
            ActivityCompat.requestPermissions(activityParam, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_ASK_PERMISSIONS)
        } else {
            // If permissions are already in place, launch the Landmarks Activity
            val intent = Intent(this@MainActivity, LandmarksActivity::class.java)
            // This tag lets the Landmark activity know that it's going to use location
            intent.putExtra("parent", "useLocation")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission granted, start landmark Activity like above
                val intent = Intent(this@MainActivity, LandmarksActivity::class.java)
                intent.putExtra("parent", "useLocation")
                startActivity(intent)
            } else {
                // If not, tell user they didn't grant permission, and how to do that
                Toast.makeText(this, R.string.noPermission, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // begin setting up app layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_name);

        closest_station_button.setOnClickListener {
            // check permissions before using closest station
            checkPermissions(this)
        }

        select_station_button.setOnClickListener {
            // go to Metro station picker
            val intent = Intent(this@MainActivity, MetroStationsActivity::class.java)
            startActivity(intent)
        }

        favorites_button.setOnClickListener() {
            // go to Favorites list
            val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
            startActivity(intent)
        }

    }
}
