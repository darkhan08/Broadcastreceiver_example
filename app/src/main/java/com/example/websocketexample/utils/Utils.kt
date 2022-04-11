package com.example.websocketexample.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.websocketexample.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

const val dateFormat: String = "dd/M/yyyy hh:mm:ss"


private fun requestPermissionLauncher(mainActivity: MainActivity, permission: String) =
    mainActivity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.i("Permission: ", "$$permission Granted")
        } else {
            Log.i("Permission: ", "$permission Denied")
        }
    }

fun permissionCheck(context: Context, mainActivity: MainActivity) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        val permission = "Phone State permission"
        requestPermissionLauncher(mainActivity, permission).launch(
            Manifest.permission.READ_PHONE_STATE
        )
    }
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CALL_LOG
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        val permission = "Read Call Log permission"
        requestPermissionLauncher(mainActivity, permission).launch(
            Manifest.permission.READ_CALL_LOG
        )
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}