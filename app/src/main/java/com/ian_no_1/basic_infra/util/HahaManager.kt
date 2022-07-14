package com.ian_no_1.basic_infra.util

import android.util.Log
import javax.inject.Inject

interface HahaManager {
    fun sayHaha()
}

class HahaManagerImpl @Inject constructor() : HahaManager {

    override fun sayHaha() {
        Log.d("haa", "haha in ${hashCode()}")
    }
}