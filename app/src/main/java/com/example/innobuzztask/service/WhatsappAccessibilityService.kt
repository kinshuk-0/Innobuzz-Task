package com.example.innobuzztask.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class WhatsappAccessibilityService : AccessibilityService() {
    private val mContentDesc = "WhatsApp"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null) {
                // Get the package name of the foreground window
            val contentDesc: String = event.contentDescription.toString()

            Log.d("SERVICE@@", "Application name = " + contentDesc)


            if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED && contentDesc == mContentDesc) {
                Toast.makeText(applicationContext, "WhatsApp Launched", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    override fun onInterrupt() {
        Log.e("SERVICE@@", "Something went wrong")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_FOCUSED

            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN

            notificationTimeout = 100
        }

        this.serviceInfo = info

        Log.d("SERVICE@@", "connected")
    }

}