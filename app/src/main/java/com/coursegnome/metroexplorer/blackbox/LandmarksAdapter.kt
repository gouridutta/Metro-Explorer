package com.coursegnome.metroexplorer.blackbox

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursegnome.metroexplorer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.landmark_item.view.*

class LandmarksAdapter(var landmarksData: ArrayList<Landmark>, var context: Context) : RecyclerView.Adapter<LandmarksAdapter.ViewHolder>() {

    lateinit var itemClickListener: OnItemClickListener

    override fun getItemCount () : Int {
        return landmarksData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.landmark_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val landmark = landmarksData[position]
        holder.itemView.landmarkName.text = landmark.name
        Picasso.with(context).load(landmark.image_url).into(holder.itemView.backgroundImage)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }
        override fun onClick(view: View) = itemClickListener.onItemClick(itemView, adapterPosition)

    }
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}