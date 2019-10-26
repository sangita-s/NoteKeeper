package com.lyeng.notekeeper

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeHolder>
//private to use it throughout the class
class TeaRecyclerAdapter(private val context: Context, private val teas: List<TeaInfo>) :
    RecyclerView.Adapter<TeaRecyclerAdapter.TeaHolder>() {

    private val layoutInflator = LayoutInflater.from(context)
    private var onTeaSelectedListener: OnTeaSelectedListener? = null

    interface OnTeaSelectedListener {
        fun onTeaSelected(tea: TeaInfo)
    }

    fun setOnSelectedListener(listener: OnTeaSelectedListener) {
        onTeaSelectedListener = listener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeaHolder {
//        View itemView = LayoutInflater.from(pViewGroup.getContext())
//            .inflate(R.layout.coffee_item, pViewGroup, false);
//        return new CoffeeHolder(itemView);
        val itemView = layoutInflator.inflate(R.layout.item_tea_list, p0, false)
        return TeaHolder(itemView)
    }

//    override fun getItemCount(): Int {
//        return teas.size
//    }

    override fun getItemCount() = teas.size

    override fun onBindViewHolder(holder: TeaHolder, position: Int) {
        val tea = teas[position]
        holder.textTeaType?.text = tea.textTeaType?.teaTypeName
        holder.textTeaName?.text = tea.textTeaName
        holder.teaPosition = position
        holder.color.setBackgroundColor(tea.color)
    }

    //public class CoffeeHolder extends RecyclerView.ViewHolder
    inner class TeaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTeaType = itemView.findViewById<TextView?>(R.id.textTeaType)
        val textTeaName = itemView.findViewById<TextView?>(R.id.textTeaName)
        var teaPosition = 0
        var color: View = itemView.findViewById(R.id.noteColor)

        init {
            itemView.setOnClickListener {
                //inner - gives access to context
                onTeaSelectedListener?.onTeaSelected(teas[teaPosition])
                val intent = Intent(context, TeaActivity::class.java)
                intent.putExtra(NOTE_POSITION, teaPosition)
                context.startActivity(intent)
            }
        }
    }
}