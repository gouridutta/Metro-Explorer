package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.util.Log
import com.koushikdutta.ion.Ion
import org.json.JSONObject

class FetchMetroStationsManager(val context : Context) {

    val WMTA_KEY = "bf49fc0455044df4a0add492c80eaaf9";

    val TAG = "FetchMetroStations"

    val WMTA_URL = arrayOf(
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=RD",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=YL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=GR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=BL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=OR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=SV")

    fun loadStationData () {
        for (y in 0 until WMTA_URL.size-1) {
            val x = y

            Ion.with(context)
                .load(WMTA_URL[y])
                .addHeader("api_key", WMTA_KEY)
                .asJsonObject()
                .setCallback({ error, result ->
                    error?.let {
                        Log.e(TAG, it.message)
                    }
                    result?.let {
                        val results = it.getAsJsonArray("Stations")
                        for (i in 0..results.size() - 1) {
                            val station = results.get(i).asJsonObject
                            val lat = station.get("Name").asString
                            Log.d(TAG, " Name: $x and $lat")
                        }

                    }
                })
        }
    }

}