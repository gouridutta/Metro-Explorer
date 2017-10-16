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
    lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var landmark : Landmark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Landmark Title");
        setContentView(R.layout.activity_landmark_detail)

        //initialize Persistance Manager
        persistanceManager = PersistanceManager(this)

        landmarkdetail_toolbar.setTitleTextColor(ContextCompat.getColor(this@LandmarkDetailActivity, R.color.colorWhite))
        setSupportActionBar(landmarkdetail_toolbar)
        //supportActionBar?.title = "Add Favorite"
        landmark = intent.getSerializableExtra("landmarkObject") as Landmark

        landmarkName.text = intent.getStringExtra("landmarkName")
        supportActionBar?.title = landmarkName.text
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
            yelpIntent.setData(Uri.parse(intent.getStringExtra("yelp_url")))
            startActivity(yelpIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landmarkdetail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id  = item?.itemId
        when (id) {
            R.id.favorite -> {
                val landmarkname = intent.getStringExtra("landmarkName")
                if (persistanceManager.saveFavoriteLandmarks(landmark)) {
                    Toast.makeText(this@LandmarkDetailActivity, landmarkname + " saved as favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this@LandmarkDetailActivity, landmarkname + " is already saved as favorite", Toast.LENGTH_SHORT).show();
                }
            }
//            R.id.action_settings -> {
//               // Toast.makeText(this@LandmarkDetailActivity,"share", Toast.LENGTH_SHORT).show();
//                val smsUri = Uri.parse("smsto: " )
//                val intent = Intent(Intent.ACTION_SENDTO)
//                intent.setData(smsUri)
//                intent.setType("vnd.android-dir/mms-sms")
//                intent.putExtra("sms_body", "to");
//
//                //if(intent.resolveActivity(packageManager? != null)) {
//                    startActivity(intent);
//                //}
//
//
//            }
//            else -> {
//                //
//            }
//        }
//
//
//        return super.onOptionsItemSelected(item)
    }



}
