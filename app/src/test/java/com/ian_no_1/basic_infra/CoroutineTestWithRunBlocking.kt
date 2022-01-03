package com.ian_no_1.basic_infra

import com.ian_no_1.basic_infra.ui.HahaViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class CoroutineTestWithRunBlocking {

    @ExperimentalTime
    @Test
    fun testSuspendFunRunBlocking() {
        // run blocking would keep all delay time
        val runBlockingDuration = measureTime {
            runBlocking {
                assert("haha" == HahaViewModel().getHaha())
            }
        }
        assert(runBlockingDuration.inWholeMilliseconds >=5000)

        val runBlockingTestDuration = measureTime {
            // blocking test would skip the delay
            runBlockingTest {
                assert("haha" == HahaViewModel().getHaha())
            }
        }
        assert(runBlockingTestDuration.inWholeMilliseconds <1000)

    }

}