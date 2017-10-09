package com.coursegnome.metroexplorer.blackbox

import java.io.Serializable

/**
 * Created by timtraversy on 9/30/17.
 */
class StationData (val name : String, val lines : ArrayList<String>, val lat : Float, val long : Float) : Serializable