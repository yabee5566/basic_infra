package com.ian_no_1.basic_infra.play

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.getOrElse
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class PlayGeneralizedPriorityChannel {
}


fun playGeneralizedSelect() {
    val ch1 = Channel<String>(100, BufferOverflow.DROP_OLDEST)
    val ch2 = Channel<String>(100, BufferOverflow.DROP_OLDEST)
    val ch3 = Channel<String>(100, BufferOverflow.DROP_OLDEST)
    GlobalScope.launch {
        var i = 1
        while (true) {
            val packet = "aaa_$i"
            ch1.send(packet)
            println("send $packet")
            delay(1000)
            i++
        }
    }
    GlobalScope.launch {
        var i = 1
        while (true) {
            val packet = "bbb_$i"
            ch2.send(packet)
            println("send $packet")
            delay(2000)
            i++
        }
    }
    GlobalScope.launch {
        var i = 1
        while (true) {
            val packet = "ccc_$i"
            ch3.send(packet)
            println("send $packet")
            delay(3000)
            i++
        }
    }
    val resultCh = GlobalScope.produce {
        val chList = listOf(ch1, ch2, ch3)
        while (isActive) {
            select<Unit> {
                chList.forEach {
                    it.onReceiveCatching {
                        send(it.getOrElse { "" })
                    }
                }
            }
        }
    }
    GlobalScope.launch {
        resultCh.receiveAsFlow().collect {
            println("received    $it")
            delay(1000)
        }
    }
    Thread.sleep(55555555)
}

fun main() {
    playGeneralizedSelect()
}
