package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.model.Landmark
import com.coursegnome.metroexplorer.tasks.PersistanceManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_landmark_detail.*

class LandmarkDetailActivity : AppCompatActivity() {
    private lateinit var persistanceManager: PersistanceManager
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var landmark: Landmark

    override fun onCreate(savedInstanceState: Bundle?) {

        // Begin setting up layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_detail)

        //initialize Persistance Manager
        persistanceManager = PersistanceManager(this)

        // Add toolbar
        landmarkdetail_toolbar.setTitleTextColor(ContextCompat.getColor(this@LandmarkDetailActivity, R.color.colorWhite))
        setSupportActionBar(landmarkdetail_toolbar)
        landmark = intent.getSerializableExtra("landmarkObject") as Landmark

        // Update name and address in layout
        landmarkName.text = intent.getStringExtra("landmarkName")
        supportActionBar?.title = landmarkName.text
        address.text = intent.getStringExtra("address")

        // Update distance in layout
        val stationName = intent.getStringExtra("stationName")
        var meters = intent.getFloatExtra("distance", 0.0f)
        if (meters == 0.0f) {
            distanceLabel.visibility = View.GONE
            distance.visibility = View.GONE
        } else {
            meters *= 0.00062f
            val miles = String.format("%.2f", meters)
            distance.text = getString(R.string.distance_feet, miles, stationName)
        }

        // Update phone in layout (hide if not available)
        phone.text = intent.getStringExtra("phone")
        if (phone.text == "") {
            phoneLabel.visibility = View.GONE
            phone.visibility = View.GONE
        }

        // Update image in layout (load placeholder if Yelp doesn't provide)
        if (intent.getStringExtra("image_url") != "") {
            Picasso.with(this).load(intent.getStringExtra("image_url")).into(image)
        } else {
            image.setImageResource(R.drawable.placeholder)
        }

        // If address clicked, launch Google Maps Walking directions
        address.setOnClickListener {
            val address = Uri.encode(intent.getStringExtra("address"))
            // Navgiation with walking mode
            val uri = Uri.parse("google.navigation:q=" + address + "&mode=w")
            val mapsIntent = Intent(Intent.ACTION_VIEW, uri)
            mapsIntent.setPackage("com.google.android.apps.maps")
            if (mapsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapsIntent)
            } else {
                // If Google Maps can't be accessed
                Toast.makeText(this, R.string.noMaps, Toast.LENGTH_SHORT).show()
            }
        }

        yelp_link.setOnClickListener {
            val yelpIntent = Intent()
            yelpIntent.setAction(Intent.ACTION_VIEW)
            yelpIntent.setData(Uri.parse(intent.getStringExtra("yelp_url")))
            startActivity(yelpIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landmarkdetail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.favorite -> {
                val landmarkname = intent.getStringExtra("landmarkName")
                if (persistanceManager.saveFavoriteLandmarks(landmark)) {
                    Toast.makeText(this@LandmarkDetailActivity, landmarkname + " saved as favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this@LandmarkDetailActivity, landmarkname + " is already saved as favorite", Toast.LENGTH_SHORT).show();
                }
            }
            R.id.action_settings -> {
                Toast.makeText(this@LandmarkDetailActivity, "share", Toast.LENGTH_SHORT).show();
            }
            else -> {
                //
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
