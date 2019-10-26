package com.lyeng.notekeeper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_tea_list.*
import kotlinx.android.synthetic.main.content_tea_list.*

class TeaListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tea_list)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            val activityIntent = Intent(this, TeaActivity::class.java)
//            startActivity(activityIntent)
//        }

        fab.setOnClickListener{
            startActivity(Intent(this, TeaActivity::class.java))
        }

//        //listTea from layout
//        listTea.adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            DataManager.listOfTeas
//        )
//
//        listTea.setOnItemClickListener { parent, view, position, id ->
//            val activityIntent = Intent(this, TeaActivity::class.java)
//            activityIntent.putExtra(NOTE_POSITION, position)
//            startActivity(activityIntent)
//        }

        listItems.layoutManager = LinearLayoutManager(this)
        listItems.adapter = TeaRecyclerAdapter(this, DataManager.listOfTeas)

    }

    override fun onResume() {
        super.onResume()
//        (listTea.adapter as ArrayAdapter<TeaInfo>).notifyDataSetChanged()
        //ref to adapert is listItems.adapter
        listItems.adapter?.notifyDataSetChanged()
    }
}
