package com.lyeng.notekeeper

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DataManagerAddNoteTest {

    @Before
    fun setUp() {
        DataManager.listOfTeas.clear()
        DataManager.initializeNotes()
    }

    @Test
    fun addNote() {
        val tea = DataManager.hashmapOfTeaType.get("04_green_tea")!!//null check - throw an exception - not in PR
        val teaTitle = "Green Matcha Tea"
        val teaMsg = "Matcha is from Japan"

        val index = DataManager.addNote(tea, teaTitle, teaMsg)
        val reqdTea = DataManager.listOfTeas[index]

        assertEquals(tea, reqdTea.textTeaType)
        assertEquals(teaTitle, reqdTea.textTeaName)
        assertEquals(teaMsg, reqdTea.textMessage)
    }

    @Test
    fun findSimilarNotes(){
        val tea = DataManager.hashmapOfTeaType.get("04_green_tea")!!
        val teaTitle = "Green Matcha Tea"
        val teaMsg = "Matcha is from Japan"
        val teaMsg2 = "Matcha is from Japan 2"

        val index = DataManager.addNote(tea, teaTitle, teaMsg)
        val index2 = DataManager.addNote(tea, teaTitle, teaMsg2)

        val tea1 = DataManager.findNote(tea, teaTitle, teaMsg)
        val foundIndex = DataManager.listOfTeas.indexOf(tea1)
        assertEquals(index, foundIndex)

        val tea2 = DataManager.findNote(tea, teaTitle, teaMsg2)
        val foundIndex2 = DataManager.listOfTeas.indexOf(tea2)
        assertEquals(index2, foundIndex2)
    }
}