package com.coursegnome.metroexplorer.blackbox

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.koushikdutta.ion.Ion
import org.json.JSONObject
import android.R.id.edit
import android.provider.Telephony.Mms.Part.FILENAME
import java.io.*


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

        val stations = ArrayList<station>()

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
                            if (!isInList(name, stations)) {
                                val newStation = station(name,lineList, lat, long)
                                stations.add(newStation)
                            }
                        }
                    }
                })
        }

        val FILENAME = "stations"

        val fos : FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(stations)
        oos.close()
        fos.close()

        val fis : FileInputStream = context.openFileInput(FILENAME)
        val ois = ObjectInputStream(fis)
        ois.readObject()
        ois.close()
        fis.close()


//        val editor = sharedPref.edit()
//        editor.putString("Key1", "Hello")
//        editor.apply()

    }

    fun isInList (name : String, list : ArrayList<station>) : Boolean {
        for (i in 0 until list.size) {
            if (list.get(i).name.equals(name)) {
                return true
            }
            return false
        }
        return false
    }

}