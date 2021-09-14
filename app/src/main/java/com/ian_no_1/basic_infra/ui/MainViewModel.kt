package com.ian_no_1.basic_infra.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ian_no_1.basic_infra.network.DuckApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    duckApi: DuckApi
) : ViewModel() {
    init {
        viewModelScope.launch {
            Log.d("aaaa", "aaaaaaa")
            val duck = duckApi.getRandomDuck()
            Log.d("aaaa", "$duck")
        }
    }

    fun haha() {
        Log.d("aaaa", "haha")
    }


}