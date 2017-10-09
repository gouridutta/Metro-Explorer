package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.coursegnome.metroexplorer.R
import kotlinx.android.synthetic.main.list_item.view.*

class MetroStationsAdapter (private var context: Context) :
    RecyclerView.Adapter<MetroStationsAdapter.ViewHolder>() {

    lateinit var fetchMetro : FetchMetroStationsManager
    lateinit var itemClickListener: OnItemClickListener

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
                "SB" -> holder.itemView.silvercircle.visibility = View.VISIBLE
            }
        }
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView), View.OnClickListener {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }
        override fun onClick(view: View?) = itemClickListener.onItemClick(itemView, adapterPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position: Int )
    }

    fun setOnItemClickListener (itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}