package com.coursegnome.metroexplorer.tasks

import android.content.Context
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.coursegnome.metroexplorer.model.StationData

class FetchMetroStationsManager(val context: Context) {

    // This class functions by performing the API call only once the first time the
    // loadStationData fun is called. Otherwise it just reads the data from internal storage

    fun getAllStations(): ArrayList<StationData> {

        val array = Parser().parse(context.assets.open("StationData.json")) as JsonObject
        val more = array.get("Stations") as JsonArray<String>

        val stationList = ArrayList<StationData>()

        for (i in 0 until more.size) {
            val newStationObject = more.get(i) as JsonObject
            val name = newStationObject["Name"].toString()
            val lines = ArrayList<String>()
            newStationObject["LineCode1"]?.let { lines.add(newStationObject["LineCode1"].toString()) }
            newStationObject["LineCode2"]?.let { lines.add(newStationObject["LineCode2"].toString()) }
            newStationObject["LineCode3"]?.let { lines.add(newStationObject["LineCode3"].toString()) }
            newStationObject["LineCode4"]?.let { lines.add(newStationObject["LineCode4"].toString()) }
            val lat = newStationObject["Lat"].toString().toFloat()
            val lon = newStationObject["Lon"].toString().toFloat()
            val newStation = StationData(name, lines, lat, lon, 0.0)
            stationList.add(newStation)
        }

        return stationList
    }

}