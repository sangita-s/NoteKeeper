package com.lyeng.notekeeper

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`

//class RecyclerViewItemCountAssertion(val expectedCount: Int) : ViewAssertion {
//    @Override
//    fun check(view: View, noViewFoundException: NoMatchingViewException?) {
//        if (noViewFoundException != null) {
//            throw noViewFoundException
//        }
//        val recyclerView = view as RecyclerView
//        val adapter = recyclerView.adapter
//        assertThat(adapter!!.itemCount, `is`(expectedCount))
//    }
//}