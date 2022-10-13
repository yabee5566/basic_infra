package com.ian_no_1.basic_infra.play

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import java.lang.Thread.sleep
import java.util.concurrent.PriorityBlockingQueue

class PlayWIthPriorityQueueChannel {
}

interface PriorityChannel<T> {
    fun observe(): Flow<T>
    fun cancel()
}

class PriorityChannelImpl<T> constructor(
    comparator: Comparator<T>,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val priorityQueue = PriorityBlockingQueue(
        128,
        comparator
    )

    fun offer(item:T){
        priorityQueue.offer(item)
    }

    fun observe(): Flow<T> = coroutineScope.produce {
        while (isActive) {
            val item = withContext(Dispatchers.IO) { priorityQueue.take() }
            send(item)
        }
    }.receiveAsFlow()

    fun cancel(){
        coroutineScope.cancel()
        priorityQueue.clear()
    }
}

fun playPriorityQueueChannel(){
    val comparator = Comparator<Int> { item1, item2 ->
        when {
            item1 == item2 ->0
            item1 == null ->  -1
            item2 == null ->  1
            else -> item1 - item2
        }
    }
    val priorityChannel = PriorityChannelImpl<Int>(comparator =comparator)
    GlobalScope.launch {
        val list = listOf(1,2,3,4,5,6,7,8)
        for (i in list) {
            delay(200)
            priorityChannel.offer(i)
        }
    }
    GlobalScope.launch {
        priorityChannel.observe().collect{
            println("receive $it")
            delay(1000)
        }
    }
    GlobalScope.launch {
        delay(5000)
        priorityChannel.cancel()
        priorityChannel.observe().collect{
            println("receive $it")
            delay(1000)
        }
    }
    sleep(555555)
}

fun main() {
    playPriorityQueueChannel()
}
