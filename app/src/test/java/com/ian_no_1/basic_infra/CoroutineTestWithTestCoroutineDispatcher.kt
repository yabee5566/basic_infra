package com.ian_no_1.basic_infra

import com.ian_no_1.basic_infra.ui.HahaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CoroutineTestWithTestCoroutineDispatcher {

    @ExperimentalCoroutinesApi
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testSetHahaPropertyByDelay(){
        val vm = HahaViewModel()
        vm.setHahaPropertyByMainCoroutine()
        assert(vm.hahaProperty == "haha")

    }


}