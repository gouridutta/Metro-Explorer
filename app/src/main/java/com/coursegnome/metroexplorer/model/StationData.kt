package com.coursegnome.metroexplorer.model

import java.io.Serializable

class StationData(val name: String, val lines: ArrayList<String>, val lat: Float, val long: Float, var distance: Double) : Serializable