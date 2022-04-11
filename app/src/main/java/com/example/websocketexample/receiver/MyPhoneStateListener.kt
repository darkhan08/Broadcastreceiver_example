package com.example.websocketexample.receiver

import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.example.websocketexample.utils.dateFormat
import com.example.websocketexample.utils.getCurrentDateTime
import com.example.websocketexample.utils.toString

class MyPhoneStateListener(private val context: Context?) : PhoneStateListener() {

    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("DEBUG", "IDLE")
                val date = getCurrentDateTime().toString(dateFormat)
                val customIntent = Intent("service.to.activity.transfer")
                customIntent.putExtra("idleDate", date)
                context?.sendBroadcast(customIntent)
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("DEBUG", "OFFHOOK")
                val date = getCurrentDateTime().toString(dateFormat)
                val customIntent = Intent("service.to.activity.transfer")
                customIntent.putExtra("offhookDate", date)
                context?.sendBroadcast(customIntent)
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("DEBUG", "RINGING")
                val date = getCurrentDateTime().toString(dateFormat)
                val customIntent = Intent("service.to.activity.transfer")
                customIntent.putExtra("phone_number", incomingNumber)
                customIntent.putExtra("ringingDate", date)
                context?.sendBroadcast(customIntent)
            }
        }
    }
}
