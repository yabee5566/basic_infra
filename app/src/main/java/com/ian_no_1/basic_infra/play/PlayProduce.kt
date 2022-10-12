package com.ian_no_1.basic_infra.play

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.IOException
import java.lang.Thread.sleep

class PlayProduce {
}

fun playFlow() {
    val countDownFlow = flow {
        var a = 1
        while (true) {
            emit(a)
            a++
            delay(1000)
        }
    }
    GlobalScope.launch {
        countDownFlow.collect {
            println("aaaaa $it")
            delay(5000)
        }
    }
    GlobalScope.launch {
        countDownFlow.collect {
            println("bbb $it")
            delay(2000)
        }
    }
    sleep(100000)
}


fun playProduce() {
    val countDownChannel:ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
        var a = 1
        while (true) {
            delay(1000)
            send(a)
            a++
        }
    }


    GlobalScope.launch {
        countDownChannel.receiveAsFlow().collect {
            println("aaaaa $it")
            delay(5000)
        }
    }
    GlobalScope.launch {
        countDownChannel.receiveAsFlow().collect {
            println("bbb $it")
            delay(2000)
        }
    }
    sleep(100000)
}

fun main() {
    playProduce()
}