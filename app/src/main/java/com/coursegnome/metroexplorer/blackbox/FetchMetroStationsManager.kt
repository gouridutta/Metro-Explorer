package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.util.Log
import com.koushikdutta.ion.Ion
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.JsonReader
import com.coursegnome.metroexplorer.Files.readStationData
import com.coursegnome.metroexplorer.R.id.list
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.async.parser.JSONArrayParser
import org.json.JSONArray
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths


class FetchMetroStationsManager(val context : Context) {

    // This class functions by performing the API call only once the first time the
    // loadStationData fun is called. Otherwise it just reads the data from internal storage

    val WMTA_KEY = "bf49fc0455044df4a0add492c80eaaf9";

    val TAG = "FetchMetroStations"

    val FILENAME = "stations"

    val WMTA_URL = arrayOf(
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=RD",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=YL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=GR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=BL",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=OR",
            "https://api.wmata.com/Rail.svc/json/jStations?LineCode=SV")

    fun sizeOfStationObjects () : Int {
        val x = readStationData.readData(context)
        return x.size
    }

    fun getStation (position : Int) : StationData {
        var count = 0
        val fis: FileInputStream = context.openFileInput(FILENAME)
        val ois = ObjectInputStream(fis)
        try {
            while (true) {
                val x = ois.readObject()
                if (count == position && x is StationData) {
                    return x
                }
                ++count
            }
        } catch (e: EOFException) {
            // done reading, return station data
            ois.close()
            fis.close()
            val x = ArrayList<String>()

            val errorStation = StationData("Error", x, 10f, 10f)
            return errorStation
        }
    }

}