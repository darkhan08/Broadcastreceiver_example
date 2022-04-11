package com.example.websocketexample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager


class ServiceReceiver : BroadcastReceiver() {
    var telephony: TelephonyManager? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        telephony = context
            ?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val telephonyCallback = MyPhoneStateCallback()
            context?.let {
                telephony?.registerTelephonyCallback(it.mainExecutor, telephonyCallback)
            }
/*  TelephonyManager.EXTRA_INCOMING_NUMBER is deprecated in api 29 and
intent.extra.getString("incoming_number") return empty string. If you wand get caller number
you must use  InCallService (https://developer.android.com/reference/android/telephony/TelephonyManager#EXTRA_INCOMING_NUMBER)

    Next time I will implement this task with InCallService
 */
        } else {
            val phoneListener = MyPhoneStateListener(context)
            telephony?.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }
}