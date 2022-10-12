package com.ian_no_1.basic_infra.play

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.selects.select
import java.lang.Thread.sleep

class PlaySelectClause {
}

// select clause would handle the upper channel first so that we can use it to prioritize channels

fun playSelectClause() {
    val numberCh = Channel<String>(100, BufferOverflow.DROP_OLDEST)
    val charCh = Channel<String>(100, BufferOverflow.DROP_OLDEST)

    GlobalScope.launch {
        var a = 1
        while (isActive) {
            numberCh.send("$a")
            println("send >>> $a")
            delay(2000)
            a++
        }
    }
    GlobalScope.launch {
        var a = 'a'
        while (isActive) {
            charCh.send("$a")
            println("send >>> $a")
            delay(1000)
            a++
        }
    }

    val resultCh = GlobalScope.produce {
        while (isActive) {
            select<Unit> {
                numberCh.onReceiveCatching {
                    send(it.getOrElse { "" })
                }
                charCh.onReceiveCatching {
                    send(it.getOrElse { "" })
                }
            }
        }
    }
    GlobalScope.launch {
        resultCh.receiveAsFlow().collect {
            delay(3000)
            println("c1 receive <<<  $it ")
        }
    }
    sleep(200000)
}

fun main() {
    playSelectClause()
}
