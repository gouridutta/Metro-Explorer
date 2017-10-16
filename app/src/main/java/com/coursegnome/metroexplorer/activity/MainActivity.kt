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
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE_ASK_PERMISSIONS = 123

    fun checkPermissions(activityParam: Activity) {
        val hasWriteContactsPermission = ContextCompat.checkSelfPermission(activityParam, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activityParam, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_ASK_PERMISSIONS)
        } else {
            val intent = Intent (this@MainActivity, LandmarksActivity::class.java)
            intent.putExtra("parent", "useLocation")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                val intent = Intent (this@MainActivity, LandmarksActivity::class.java)
                intent.putExtra("parent", "useLocation")
                startActivity(intent)
            } else {
                // TODO tell user they can't use this feature without enabling location
                Log.d("No", "Didnt grant permissions")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Metro Explorer");

        closest_station_button.setOnClickListener {
            checkPermissions(this)
        }

        select_station_button.setOnClickListener {
            val intent = Intent (this@MainActivity, MetroStationsActivity::class.java)
            startActivity(intent)
        }

        favorites_button.setOnClickListener() {
            val intent = Intent (this@MainActivity, FavoritesActivity::class.java)
            startActivity(intent)
        }

    }
}
