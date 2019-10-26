package com.lyeng.notekeeper

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.util.Log

class NoteGetTogetherHelper(val context: Context, val lifecycle: Lifecycle) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    val tag = this::class.simpleName
    var crtLat = 0.0
    var crtLon = 0.0

    val locmanager = PseudoLocationManager(context) { lat, lon ->
        crtLat = lat
        crtLon = lon

        Log.d(tag, "Location callback Lat: $crtLat, Lon: $crtLon")
    }

    val msgManager = PseudoMessagingManager(context)
    var msgConnection: PseudoMessagingConnection? = null

    public fun sendMessage(tea: TeaInfo) {
        val getTogetherMsg =
            "$crtLat|$crtLon|${tea.textTeaType}|${tea.textTeaName}|${tea.textMessage}"
        msgConnection?.send(getTogetherMsg)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        locmanager.start()
        //async call
        msgManager.connect() { connection ->
            Log.d(tag, "Messaging connection callback - Lifecycle state: ${lifecycle.currentState}")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                msgConnection = connection
            else
                msgConnection?.disconnect()

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        locmanager.stop()
        msgConnection?.disconnect()
    }
}