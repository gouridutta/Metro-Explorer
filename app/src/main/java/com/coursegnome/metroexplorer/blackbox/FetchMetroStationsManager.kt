package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.util.Log
import com.koushikdutta.ion.Ion
import android.provider.Telephony.Mms.Part.FILENAME
import java.io.*


class FetchMetroStationsManager(val context : Context) {

    // This class functions by performing the API call only once the first time the
    // loadStationData fun is called. Otherwise it just reads the data from internal storage

    val WMTA_KEY = "bf49fc0455044df4a0add492c80eaaf9";

    val TAG = "FetchMetroStations"

    val WMTA_URL = arrayOf(
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=RD",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=YL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=GR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=BL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=OR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=SV")

    fun downloadStationData () {

        val FILENAME = "stationsx"
        val stations = ArrayList<StationData>()
        val stationsTwo = ArrayList<StationData>()

        // try to read station data from internal storage and move to stations object
        try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            // return if internal file found
            return
        } catch (e : FileNotFoundException) {
            // if the app hasn't ever fetched the API, do that and write the data to internal storage
            Log.d(TAG, "File not written yet")
        }

        for (y in 0 until WMTA_URL.size-1) {
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
                            val name = station.get("Name").asString
                            var line1 : String?
                            var line2 : String?
                            var line3 : String?
                            var line4 : String?
                            if (station.get("LineCode1").isJsonNull) {
                                line1 = null
                            } else {
                                line1 = station.get("LineCode1").asString
                            }
                            if (station.get("LineCode2").isJsonNull) {
                                line2 = null
                            } else {
                                line2 = station.get("LineCode2").asString
                            }
                            if (station.get("LineCode3").isJsonNull) {
                                line3 = null
                            } else {
                                line3 = station.get("LineCode3").asString
                            }
                            if (station.get("LineCode4").isJsonNull) {
                                line4 = null
                            } else {
                                line4 = station.get("LineCode4").asString
                            }
                            val lineList = listOf (line1, line2, line3 ,line4)
                            val lat = station.get("Lat").asFloat
                            val long = station.get("Lon").asFloat
                            Log.d(TAG, " Name: $name")
                            val newStation = StationData(name, lineList, lat, long)

                            if (!isInList(name, stations)) {
                                stations.add(newStation)
                            }
                            stationsTwo.add(newStation)
                        }

                        val fos : FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
                        val oos = ObjectOutputStream(fos)
                        for (station in stations) {
                            oos.writeObject(station)
                        }
                        oos.close()
                        fos.close()
                    }
                })
        }
    }

    // fun to check if Array List contains station with that name
    fun isInList (name : String, list : ArrayList<StationData>) : Boolean {
        for (i in 0 until list.size) {
            if (list.get(i).name.equals(name)) {
                return true
            }
            return false
        }
        return false
    }

}