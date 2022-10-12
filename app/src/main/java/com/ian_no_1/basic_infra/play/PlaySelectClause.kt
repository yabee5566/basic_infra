package com.ian_no_1.basic_infra.play

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.selects.select
import java.lang.Thread.sleep

class PlaySelectClause {
}

fun playSelectClause() {
    //val scope = CoroutineScope(Dispatchers.Default)
    val numberCh = Channel<String>(100, BufferOverflow.DROP_OLDEST)
    val charCh = Channel<String>(100, BufferOverflow.DROP_OLDEST)

    GlobalScope.launch {
        var a = 1
        while (isActive) {
            numberCh.send("$a")
            println("send >>> $a")
            delay(12000)
            a++
        }
    }
    GlobalScope.launch {
        var a = 'a'
        while (isActive) {
            charCh.send("$a")
            println("send >>> $a")
            delay(5000)
            a++
        }
    }

    val resultCh = flow {
        while(true){
            select<Unit> {
                numberCh.onReceiveCatching {
                    emit(it.getOrElse { "" })
                }
                charCh.onReceiveCatching {
                    emit(it.getOrElse { "" })
                }
            }
        }
    }
    GlobalScope.launch {
        resultCh.collect {
            println("c1 receive <<<  $it ")
        }
    }
    GlobalScope.launch {
        resultCh.collect {
            println("c2 receive <<<  $it ")
        }
    }
    sleep(200000)
}

fun main() {
    playSelectClause()
}
