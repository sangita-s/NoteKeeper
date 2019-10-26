package com.lyeng.notekeeper

import android.arch.lifecycle.ViewModel
import android.os.Bundle

class ItemsActivityViewModel : ViewModel() {
    var isNewlyCreated = true;

    var navDrawerDisplaySelectionName =
        "com.lyeng.notekeeper.ItemsActivityViewModel.navDrawerDisplaySelection"

    var navDrawerDisplaySelection = R.id.nav_tea

    var recentlyViewedNoteIdsName =
        "com.lyeng.notekeeper.ItemsActivityViewModel.recentlyViewedNoteIds"
    private val maxRecentlyViewedNotes = 5
    val recentlyViewedNotes = ArrayList<TeaInfo>(maxRecentlyViewedNotes)

    fun addToRecentlyViewedNotes(tea: TeaInfo) {
        val existingIndex = recentlyViewedNotes.indexOf(tea)
        if (existingIndex == -1) {
            //It isnt in the list. So, add one to beginning of list
            recentlyViewedNotes.add(0, tea)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            //It's in the list. SO make it the 1st member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = tea
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerDisplaySelectionName, navDrawerDisplaySelection)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntArray(recentlyViewedNoteIdsName, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(navDrawerDisplaySelectionName)
        val noteIds = savedInstanceState.getIntArray(recentlyViewedNoteIdsName)
        // * - spread operator
        val noteList = DataManager.loadNotes(*noteIds)
        recentlyViewedNotes.addAll(noteList)
    }
}