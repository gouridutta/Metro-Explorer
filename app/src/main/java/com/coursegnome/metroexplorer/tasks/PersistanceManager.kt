package com.coursegnome.metroexplorer.tasks

import Keys
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.coursegnome.metroexplorer.model.Landmark
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by gouridutta on 10/15/17.
 */
class PersistanceManager(context: Context) {
    val preference : SharedPreferences

    init {
        preference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveFavoriteLandmarks(landmark: Landmark) {
        val landmarks : HashSet<Landmark> = getSavedFavoriteLandmarks().toHashSet()
        landmarks.add(landmark)
        val editor = preference.edit()
        editor.putString(Keys.PREFERENCE_KEY, Gson().toJson(landmarks))
        editor.apply()
    }

    fun getSavedFavoriteLandmarks() : ArrayList<Landmark> {
        //get string of landmarks in JSON format
        var jsonData = preference.getString(Keys.PREFERENCE_KEY, null)

        if(jsonData == null) return ArrayList<Landmark>()
        else {
           val landmarksType = object : TypeToken<MutableList<Landmark>>() {}.type
           val favLandmarks : ArrayList<Landmark> = Gson().fromJson(jsonData, landmarksType)
            return favLandmarks
        }

    }
}