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

    fun saveFavoriteLandmarks(landmark: Landmark) : Boolean {
        val landmarks = getSavedFavoriteLandmarks()
        landmarks.sortWith(compareBy { it.name })
        if (searchIfElementAlreadyExist(landmark, landmarks)) {
            landmarks.add(landmark)
            val editor = preference.edit()
            editor.putString(Keys.PREFERENCE_KEY, Gson().toJson(landmarks))
            editor.apply()
            return true
        }
        return false
    }

    /**
     * Check if landmark is already in favorite list
     */
    fun searchIfElementAlreadyExist(toSearch : Landmark ,list: ArrayList<Landmark>) : Boolean {
        return list.binarySearch(toSearch, compareBy { it.name }, 0, list.size) < 0
    }

    fun getSavedFavoriteLandmarks() : ArrayList<Landmark> {
        //get string of landmarks in JSON format
        val jsonData = preference.getString(Keys.PREFERENCE_KEY, null)

        if(jsonData == null) return ArrayList<Landmark>()
        else {
           val landmarksType = object : TypeToken<MutableList<Landmark>>() {}.type
           val favLandmarks : ArrayList<Landmark> = Gson().fromJson(jsonData, landmarksType)
            return favLandmarks
        }

    }
}