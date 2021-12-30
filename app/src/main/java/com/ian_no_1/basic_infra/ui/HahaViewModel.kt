package com.ian_no_1.basic_infra.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


// Dummy ViewModel for testing coroutine unit test
@HiltViewModel
class HahaViewModel @Inject constructor():ViewModel(){

    suspend fun getHaha():String {
        delay(5000)
        return "haha"
    }

    var hahaProperty:String = ""

    fun setHahaPropertyByMainCoroutine() {
        viewModelScope.launch {
            hahaProperty = "haha"
        }
    }

}