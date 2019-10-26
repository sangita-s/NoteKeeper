package com.lyeng.notekeeper

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class TeaActivity : AppCompatActivity() {
    private var teaPosition = POSITION_NOT_SET

    companion object {
        private val tag = this::class.simpleName
    }
//    val tag = "TeaActivity"

//    val locManager = PseudoLocationManager(this){lat, lon ->
//        Log.d(tag, "Location callback Lat: $lat, Lon: $lon")
//    }

    val teaGetTogetherHelper = NoteGetTogetherHelper(this, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        val dm = DataManager() --DM class -> object
        val adapterTea = ArrayAdapter<TeaTypeInfo>(
            this,
            android.R.layout.simple_spinner_item,
//            dm.hashmapOfTeaType.values.toList()) --DM class -> object
            DataManager.hashmapOfTeaType.values.toList()
        )

        adapterTea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //spinnerTeaType from layout
        spinnerTeaType.adapter = adapterTea

        teaPosition =
            savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?: intent.getIntExtra(
                NOTE_POSITION,
                POSITION_NOT_SET
            )

        if (teaPosition != POSITION_NOT_SET) {
            displayNote()
        } else {
            createNewNote()
        }
    }

//    //For location manager
//    override fun onStart() {
//        super.onStart()
//        locManager.start()
//    }
//
//    override fun onStop(){
//        locManager.stop()
//        super.onStop()
//    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(NOTE_POSITION, teaPosition)
    }

    private fun displayNote() {
        if (teaPosition > DataManager.listOfTeas.lastIndex) {
            showMessage("Tea not found")
            Log.e(
                tag,
                "Invalid tea position $teaPosition, max valid pos: ${DataManager.listOfTeas.lastIndex}"
            )
            return
        }
        val tea = DataManager.listOfTeas[teaPosition]
        //below textMessage from layout
        textTeaTitle.setText(tea.textTeaName)
        textTeaText.setText(tea.textMessage)

        val teaPos = DataManager.hashmapOfTeaType.values.indexOf(tea.textTeaType)
        spinnerTeaType.setSelection(teaPos, true)
    }

    private fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun createNewNote() {
        DataManager.listOfTeas.add(TeaInfo())
        teaPosition = DataManager.listOfTeas.lastIndex
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //when - like switch. Or if , else if
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                if (DataManager.isLastNoteId(teaPosition)) {
                    showMessage("No more notes")
                } else
                    moveNext()
                true
            }
            R.id.action_get_together -> {
                teaGetTogetherHelper.sendMessage(DataManager.loadNote(teaPosition))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (teaPosition >= DataManager.listOfTeas.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_block)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun moveNext() {
        ++teaPosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        saveNote();
    }

    private fun saveNote() {
        val tea = DataManager.listOfTeas[teaPosition];
        tea.textTeaName = textTeaTitle.text.toString()
        tea.textMessage = textTeaText.text.toString()
        tea.textTeaType = spinnerTeaType.selectedItem as TeaTypeInfo
    }
}
