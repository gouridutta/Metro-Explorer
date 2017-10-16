package com.coursegnome.metroexplorer.tasks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursegnome.metroexplorer.R
import com.coursegnome.metroexplorer.model.Landmark
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.landmark_item.view.*

class LandmarksAdapter(var landmarksData: ArrayList<Landmark>, var context: Context) : RecyclerView.Adapter<LandmarksAdapter.ViewHolder>() {

    lateinit var itemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return landmarksData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.landmark_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val landmark = landmarksData[position]
        holder.itemView.landmarkName.text = landmark.name
        if (landmark.image_url != "") {
            Picasso.with(context).load(landmark.image_url).into(holder.itemView.backgroundImage)
        } else {
            holder.itemView.backgroundImage.setImageResource(R.drawable.placeholder)
        }
        holder.itemView.landmarkName.setTag(R.id.LANDMARK_NAME_TAG, landmark.name)
        holder.itemView.landmarkName.setTag(R.id.ADDRESS_TAG, landmark.location.display_address)
        holder.itemView.landmarkName.setTag(R.id.DISTANCE_TAG, landmark.distance)
        holder.itemView.landmarkName.setTag(R.id.PHONE_TAG, landmark.display_phone)
        holder.itemView.landmarkName.setTag(R.id.YELP_URL_TAG, landmark.url)
        holder.itemView.landmarkName.setTag(R.id.IMAGE_URL_TAG, landmark.image_url)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }

        override fun onClick(view: View) = itemClickListener.onItemClick(itemView, adapterPosition)

    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}