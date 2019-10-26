package com.lyeng.notekeeper

import org.junit.Assert.*
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Rule
    @JvmField
    val itemsActivity = ActivityTestRule(ItemsActivity::class.java)

    @Test
    fun selectTeaAfterNavigationDrawerChange() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_teatypes))
        val teatypeposition = 0
        onView(withId(R.id.listItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TeaTypeRecyclerAdapter.TeaTypeViewHolder>(
                teatypeposition, click()
            )
        )
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_tea))
        val teaPos = 0
        onView(withId(R.id.listItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TeaRecyclerAdapter.TeaHolder>(
                teaPos, click()
            )
        )
        val tea = DataManager.listOfTeas[teaPos]
        onView(withId(R.id.spinnerTeaType)).
            check(matches(withSpinnerText(containsString(tea.textTeaType?.teaTypeName))))
        onView(withId(R.id.textTeaTitle)).
            check(matches(withText(containsString(tea.textTeaName))))
        onView(withId(R.id.textTeaText)).
            check(matches(withText(containsString(tea.textMessage))))
            .perform(closeSoftKeyboard())
        pressBack()

//        onView(withId(R.id.listItems))
//            .check(RecyclerViewItemCountAssertion(5))

    }
}