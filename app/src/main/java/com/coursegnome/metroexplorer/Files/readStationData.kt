package com.coursegnome.metroexplorer.Files

import android.content.Context
import com.coursegnome.metroexplorer.blackbox.StationData
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * Created by timtraversy on 10/8/17.
 */
object readStationData {
    fun readData (context: Context) : StationData {
        val jsonString = context.res.file
        val br = BufferedReader (FileReader("Station Data.json"))
        val station : StationData = Gson().fromJson(br, StationData::class.java)
        return station
    }
}