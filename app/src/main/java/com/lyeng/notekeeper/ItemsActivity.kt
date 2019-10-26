package com.lyeng.notekeeper

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.app_bar_items.*
import kotlinx.android.synthetic.main.content_items.*

class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    TeaRecyclerAdapter.OnTeaSelectedListener {

    private val teaLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    //Dont create it until I call onCreate - only with val types - property executed on 1st use
    //Remembers result for future use
    //always -> make this property -> lazy
    private val teaRecyclerAdapter by lazy {
        //        TeaRecyclerAdapter(this, DataManager.listOfTeas)
        val adapter = TeaRecyclerAdapter(this, DataManager.listOfTeas)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val teatypeLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val teaTypeRecyclerAdapter by lazy {
        TeaTypeRecyclerAdapter(this, DataManager.hashmapOfTeaType.values.toList())
    }

    private val recentlyViewedTeaRecyclerAdapter by lazy {
        val adapter = TeaRecyclerAdapter(this, viewModell.recentlyViewedNotes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val viewModell by lazy {
        ViewModelProviders.of(this)[ItemsActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, TeaActivity::class.java))
        }

        if (viewModell.isNewlyCreated && savedInstanceState != null) {
            viewModell.restoreState(savedInstanceState)
        }
        viewModell.isNewlyCreated = false
        handleDisplaySelection(viewModell.navDrawerDisplaySelection)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null)
            viewModell.saveState(outState)
    }

    private fun displayTea() {
        listItems.layoutManager = teaLayoutManager
        listItems.adapter = teaRecyclerAdapter

        nav_view.menu.findItem(R.id.nav_tea).isChecked = true
    }

    private fun displayTeaType() {
        listItems.layoutManager = teatypeLayoutManager
        listItems.adapter = teaTypeRecyclerAdapter

        nav_view.menu.findItem(R.id.nav_teatypes).isChecked = true
    }

    private fun displayRecentlyViewedNotes() {
        listItems.layoutManager = teaLayoutManager
        listItems.adapter = recentlyViewedTeaRecyclerAdapter

        nav_view.menu.findItem(R.id.nav_recent_notes).isChecked = true
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_teatypes, R.id.nav_tea, R.id.nav_recent_notes -> {
                handleDisplaySelection(item.itemId)
                viewModell.navDrawerDisplaySelection = item.itemId
            }
            R.id.nav_share -> {
                handleSelection("Share to the world")
            }
            R.id.nav_send -> {
                handleSelection("Send to your world")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun handleDisplaySelection(itemId: Int) {
        when (itemId) {
            R.id.nav_teatypes -> {
                displayTeaType()
            }
            R.id.nav_tea -> {
                displayTea()
            }
            R.id.nav_recent_notes -> {
                displayRecentlyViewedNotes()
            }
        }
    }

    override fun onTeaSelected(tea: TeaInfo) {
        viewModell.addToRecentlyViewedNotes(tea)
    }

    private fun handleSelection(s: String) {
        //Any view is fine. We use our recycler view
        Snackbar.make(listItems, s, Snackbar.LENGTH_LONG).show()
    }
}
