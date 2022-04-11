package com.example.websocketexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _phoneLiveData = MutableLiveData<String>()
    val phoneLiveData: LiveData<String> get() = _phoneLiveData

    private val _ringingDateLiveData = MutableLiveData<String>()
    val ringingDateLiveData: LiveData<String> get() = _ringingDateLiveData

    private val _idleDateLiveData = MutableLiveData<String>()
    val idleDateLiveData: LiveData<String> get() = _idleDateLiveData

    private val _offhookDateLiveData = MutableLiveData<String>()
    val offhookDateLiveData: LiveData<String> get() = _offhookDateLiveData

    fun setPhone(phone: String?) {
        phone?.let {
            _phoneLiveData.postValue(it)
        }
    }

    fun setRingingDate(date: String?) {
        date?.let {
            _ringingDateLiveData.postValue(it)
        }
    }

    fun setIdleDate(date: String?) {
        date?.let {
            _idleDateLiveData.postValue(it)
        }
    }

    fun setOffhookDate(date: String?) {
        date?.let {
            _offhookDateLiveData.postValue(it)
        }
    }

    fun clear() {
        _phoneLiveData.postValue("")
        _ringingDateLiveData.postValue("")
        _idleDateLiveData.postValue("")
        _offhookDateLiveData.postValue("")
    }
}