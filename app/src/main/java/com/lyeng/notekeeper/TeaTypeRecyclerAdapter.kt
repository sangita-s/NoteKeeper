package com.lyeng.notekeeper

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TeaTypeRecyclerAdapter(
    private val context: Context,
    private val teatypes: List<TeaTypeInfo>
) : RecyclerView.Adapter<TeaTypeRecyclerAdapter.TeaTypeViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaTypeViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_teatype_list, parent, false)
        return TeaTypeViewHolder(itemView)
    }

    override fun getItemCount(): Int = teatypes.size

    override fun onBindViewHolder(holder: TeaTypeViewHolder, position: Int) {
        val teatype = teatypes[position]
        holder.textTeatype?.text = teatype.teaTypeName
        holder.teaPosition = position
    }


    inner class TeaTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTeatype = itemView.findViewById<TextView?>(R.id.textTeaType)
        var teaPosition = 0

        init {
            itemView.setOnClickListener {
                Snackbar.make(it, teatypes[teaPosition].teaTypeName, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}