package com.example.websocketexample.receiver

import android.os.Build
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
class MyPhoneStateCallback : TelephonyCallback(), TelephonyCallback.CallStateListener {
    override fun onCallStateChanged(state: Int) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("DEBUG", "IDLE")
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("DEBUG", "OFFHOOK")

            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("DEBUG", "RINGING")
            }
        }
    }

}