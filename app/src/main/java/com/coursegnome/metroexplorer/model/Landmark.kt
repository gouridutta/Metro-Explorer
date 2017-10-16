package com.coursegnome.metroexplorer.model

import java.io.Serializable

class Landmark(val name: String, val image_url: String, val url: String, val lat: Float,
               val lon: Float, val location: Location, val phone: String,
               val display_phone: String, val distance: Float) : Serializable {

    class Location(val address1: String, val city: String, val zip_code: String,
                   val country: String, val state: String, val display_address: String) : Serializable

}