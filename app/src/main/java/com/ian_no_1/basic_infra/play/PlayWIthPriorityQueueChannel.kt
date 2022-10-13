package com.ian_no_1.basic_infra.play

import android.icu.text.SymbolTable
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.lang.Thread.sleep
import java.sql.Timestamp
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

class PlayWIthPriorityQueueChannel {
}

interface PriorityChannel<T> {
    fun observe(): Flow<T>
    fun cancel()
}

class PriorityChannelImpl<T> constructor(
    comparator: (leftItem: T, rightItem: T) -> Int,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val priorityQueue = PriorityBlockingQueue(
        128,
        comparator
    )

    fun offer(item: T) {
        priorityQueue.offer(item)
    }

    fun observe(): Flow<T> = coroutineScope.produce {
        while (isActive) {
            val item = withContext(Dispatchers.IO) { priorityQueue.take() }
            send(item)
        }
    }.receiveAsFlow()

    fun cancel() {
        coroutineScope.cancel()
        //priorityQueue.clear()
    }
}

fun playPriorityQueueChannel() {
    val comparator = { item1: Int?, item2: Int? ->
        when {
            item1 == item2 -> 0
            item1 == null -> -1
            item2 == null -> 1
            else -> item1 - item2
        }
    }
    val priorityChannel = PriorityChannelImpl<Int>(comparator = { item1, item2 ->
        when {
            item1 == item2 -> 0
            else -> item1 - item2
        }
    })
    GlobalScope.launch {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        for (i in list) {
            delay(200)
            priorityChannel.offer(i)
        }
    }
    GlobalScope.launch {
        priorityChannel.observe().collect {
            println("receive $it")
            delay(1000)
        }
    }
    GlobalScope.launch {
        delay(5000)
        priorityChannel.cancel()
        priorityChannel.observe().collect {
            println("receive $it")
            delay(1000)
        }
    }
    sleep(555555)
}

sealed class Gift {
    val createTimestamp: Long = System.currentTimeMillis()

    data class Big(val id: Int) : Gift()
    data class Small(val id: Int) : Gift()

    fun isSameType(other: Gift): Boolean {
        return this is Big && other is Big ||
                this is Small && other is Small
    }
}

fun playPriorityQueueChannelWithSealedClass() {
    val comparator = { leftGift: Gift, rightGift: Gift ->
        when {
            leftGift.isSameType(rightGift) -> (leftGift.createTimestamp - rightGift.createTimestamp).toInt()
            leftGift is Gift.Big -> -1
            else -> 1
        }
    }
    val priorityChannel = PriorityChannelImpl(comparator = comparator)
    GlobalScope.launch {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        for (i in list) {
            delay(200)
            priorityChannel.offer(Gift.Big(i))
        }
    }
    GlobalScope.launch {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        for (i in list) {
            delay(200)
            priorityChannel.offer(Gift.Small(i))
        }
    }
    GlobalScope.launch {
        priorityChannel.observe().collect {
            if (it is Gift.Big) {
                delay(2000)
            } else {
                delay(1000)
            }
            println("receive $it")
        }
    }
    sleep(555555)
}


fun main() {
    playPriorityQueueChannelWithSealedClass()
}
