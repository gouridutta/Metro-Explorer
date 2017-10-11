package com.coursegnome.metroexplorer.activity

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.coursegnome.metroexplorer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_landmark_detail.*
import kotlinx.android.synthetic.main.activity_metro_stations.*

class LandmarkDetailActivity : AppCompatActivity() {

    lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Landmark Title");
        setContentView(R.layout.activity_landmark_detail)

        landmarkName.text = intent.getStringExtra("landmarkName")
        address.text = intent.getStringExtra("address")

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

        phone.text = intent.getStringExtra("phone")
        if (phone.text == "") {
            phoneLabel.visibility = View.GONE
            phone.visibility = View.GONE
        }

        if (intent.getStringExtra("image_url") != "") {
            Picasso.with(this).load(intent.getStringExtra("image_url")).into(image)
        } else {
            image.setImageResource(R.drawable.placeholder)
        }

        address.setOnClickListener {
            val mapsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("address")))
            mapsIntent.setPackage("com.google.android.apps.maps")
            // TODO this is currently crashing App, need to set up Google Maps API
//            if (mapsIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(mapsIntent);
//            }
        }

        yelp_link.setOnClickListener {
            val yelpIntent = Intent()
            yelpIntent.setAction(Intent.ACTION_VIEW)
//            yelpIntent.putExtra(Intent.parseUri(intent.getStringExtra("yelp_url"), ))
            yelpIntent.setData(Uri.parse(intent.getStringExtra("yelp_url")))
            startActivity(yelpIntent)
        }

    }


}
