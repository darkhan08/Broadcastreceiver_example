package com.example.websocketexample.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.websocketexample.databinding.ActivityMainBinding
import com.example.websocketexample.utils.permissionCheck
import com.example.websocketexample.websocket.SocketHandler
import io.socket.client.Socket


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mSocket: Socket
    private lateinit var viewModel: MainViewModel

    private var callerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        permissionCheck(context = this, this)
        setUpViewModel()
        setUpView()
        setUpReceiver()
    }

    private fun setUpViewModel() = binding.run {
        var isCallerIdEqualNumber = false
        viewModel.phoneLiveData.observe(this@MainActivity, Observer {
            if (it == callerId) {
                isCallerIdEqualNumber = true
                textNumber.text = it
            } else isCallerIdEqualNumber = false
        })
        viewModel.ringingDateLiveData.observe(this@MainActivity, Observer {
            if (isCallerIdEqualNumber) textDate.text = it
        })
        viewModel.idleDateLiveData.observe(this@MainActivity, Observer {
            if (isCallerIdEqualNumber) textOutDate.text = it
        })
        viewModel.offhookDateLiveData.observe(this@MainActivity, Observer {
            if (isCallerIdEqualNumber) textPickupDate.text = it
        })
    }

    private fun setUpReceiver() = binding.run {
        val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val phoneNumber = intent.extras?.getString("phone_number")
                val ringingDate = intent.extras?.getString("ringingDate")
                val idleDate = intent.extras?.getString("idleDate")
                val offhookDate = intent.extras?.getString("offhookDate")

                viewModel.apply {
                    setPhone(phoneNumber)
                    setRingingDate(ringingDate)
                    setIdleDate(idleDate)
                    setOffhookDate(offhookDate)
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter("service.to.activity.transfer"));
    }

    private fun initSocket() {
        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        mSocket = SocketHandler.getSocket()
    }


    private fun setUpView() = binding.run {
        initSocket()

        counterBtn.setOnClickListener {
            mSocket.emit("counter")
        }

        btnClear.setOnClickListener {
            viewModel.clear()
            callerId = ""
            textCallerId.text = callerId
        }

        mSocket.on("counter") { args ->
            if (args[0] != null) {
                callerId = args[0] as String
                runOnUiThread {
                    textCallerId.text = callerId
                }
            }
        }
    }
}