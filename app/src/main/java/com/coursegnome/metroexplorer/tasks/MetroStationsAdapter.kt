package com.coursegnome.metroexplorer.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.model.StationData
import kotlinx.android.synthetic.main.station_item.view.*

class MetroStationsAdapter (var stationData: ArrayList<StationData>) :
    RecyclerView.Adapter<MetroStationsAdapter.ViewHolder>() {

    lateinit var itemClickListener: OnItemClickListener

    override fun getItemCount () : Int {
        return stationData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.station_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stationData[position]
        holder.itemView.placeName.text = station.name
        holder.itemView.placeName.setTag(R.id.LAT_TAG, station.lat)
        holder.itemView.placeName.setTag(R.id.LON_TAG, station.long)
        holder.itemView.redcircle.visibility = View.GONE
        holder.itemView.yellowcircle.visibility = View.GONE
        holder.itemView.greencircle.visibility = View.GONE
        holder.itemView.bluecircle.visibility = View.GONE
        holder.itemView.orangecircle.visibility = View.GONE
        holder.itemView.silvercircle.visibility = View.GONE
        for (line in station.lines) {
            when (line) {
                "RD" -> holder.itemView.redcircle.visibility = View.VISIBLE
                "YL" -> holder.itemView.yellowcircle.visibility = View.VISIBLE
                "GR" -> holder.itemView.greencircle.visibility = View.VISIBLE
                "BL" -> holder.itemView.bluecircle.visibility = View.VISIBLE
                "OR" -> holder.itemView.orangecircle.visibility = View.VISIBLE
                "SV" -> holder.itemView.silvercircle.visibility = View.VISIBLE
            }
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView), View.OnClickListener {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }
        override fun onClick(view: View) = itemClickListener.onItemClick(itemView, adapterPosition)

    }
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}