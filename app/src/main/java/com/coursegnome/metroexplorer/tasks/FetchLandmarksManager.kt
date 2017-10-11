package com.coursegnome.metroexplorer.tasks

import android.content.Context
import android.util.Log
import com.coursegnome.metroexplorer.model.Landmark
import com.koushikdutta.ion.Ion

class FetchLandmarksManager (val context : Context) {

    var YELP_URL : String = "https://api.yelp.com/v3/businesses/search?"
    val TAG = "FetchLandmarksStations"
    val YELP_KEY = "Bearer cHXmBDEtzHsfVObgxTeS6FkXiSuA67mh5v4AVdU0BvDd0Zm40y191eYLdmmUBsyFfeuPxQ-_71qqVWajD-jh6XV_Vcop7G94aLCY5DS_d6mjiqvRQTDN5U_wIVPAWXYx"

    var yelpCompleted : YelpSearchCompletedListener? = null

    interface YelpSearchCompletedListener {
        fun landmarksLoaded(landmarks : ArrayList<Landmark>)
    }

    fun fetchLandmarks (lat :Float, lon: Float ) {

        YELP_URL = YELP_URL + "term=landmark&latitude=$lat&longitude=$lon&sort_by=distance&limit=10"

        val landmarks = ArrayList<Landmark>()

        Ion.with(context)
                .load(YELP_URL)
                .addHeader("Authorization", YELP_KEY)
                .asJsonObject()
                .setCallback({ error, result ->
                    error?.let {
                        Log.e(TAG, it.message)
                    }
                    result?.let {
                        val results = it.getAsJsonArray("businesses")
                        for (i in 0..results.size() - 1) {
                            val landmark = results.get(i).asJsonObject
                            val location = landmark.get("location").asJsonObject
//                            val address1 = location.get("address1").asString
                            val city = location.get("city").asString
                            val zip_code = location.get("zip_code").asString
                            val country = location.get("country").asString
                            val state = location.get("state").asString
                            val display_addressArray = location.get("display_address").asJsonArray
                            var display_address = display_addressArray[0].asString
                            for (i in 1 until display_addressArray.size()) {
                                display_address += "\n${display_addressArray[i].asString}"
                            }
                            val newLocation = Landmark.Location ("Empty", city, zip_code, country,
                                    state, display_address)

                            val name = landmark.get("name").asString
                            val image_url = landmark.get("image_url").asString
                            val url = landmark.get("url").asString

                            val coordinates = landmark.get("coordinates").asJsonObject
                            val lat = coordinates.get("latitude").asFloat
                            val lon = coordinates.get("longitude").asFloat

                            val phone = landmark.get("phone").asString
                            val display_phone = landmark.get("display_phone").asString
                            val distance = landmark.get("distance").asFloat

                            val newLandmark = Landmark(name, image_url, url, lat, lon, newLocation,
                                    phone, display_phone, distance)

                            landmarks.add(newLandmark)
                        }
                        yelpCompleted?.landmarksLoaded(landmarks)
                    }
                })
    }

}