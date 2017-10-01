package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.list_item.view.*

class MetroStationsAdapter (private var context: Context) :
    RecyclerView.Adapter<MetroStationsAdapter.ViewHolder>() {

    lateinit var fetchMetro : FetchMetroStationsManager

    override fun getItemCount () : Int {
        fetchMetro = FetchMetroStationsManager(context)
        return fetchMetro.sizeOfStationObjects()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fetchMetro = FetchMetroStationsManager(context)
        val station = fetchMetro.getStation(position)
        holder.itemView.placeName.text = station.name
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView)

}