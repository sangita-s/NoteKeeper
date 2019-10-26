package com.lyeng.notekeeper

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard

@RunWith(AndroidJUnit4::class)
class CreateNewTeaTest {

    //Property is a method in kotlin. So, represent it as a java field
    @Rule
    @JvmField
    val teaListActivity = ActivityTestRule(ItemsActivity::class.java)

    @Test
    fun createNewNote() {
        val teaType = DataManager.hashmapOfTeaType["04_green_tea"]
        val teaTitle = "Green Matcha Tea"
        val teaMsg = "Matcha is from Japan"

        onView(withId(R.id.fab)).perform(click())

        //Adapterview , do ondata
        onView(withId(R.id.spinnerTeaType)).perform(click())
        onData(allOf(instanceOf(TeaTypeInfo::class.java), equalTo(teaType))).perform(click())
        onView(withId(R.id.textTeaTitle)).perform(typeText(teaTitle))
        onView(withId(R.id.textTeaText)).perform(typeText(teaMsg)).perform(closeSoftKeyboard())
        pressBack()

        val newNote = DataManager.listOfTeas.last()
        assertEquals(teaType, newNote.textTeaType)
        assertEquals(teaTitle, newNote.textTeaName)
        assertEquals(teaMsg, newNote.textMessage)

    }
}