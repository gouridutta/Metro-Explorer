package com.coursegnome.metroexplorer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class TravelListAdapter(private var context: Context) : RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
return 0;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {


    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}