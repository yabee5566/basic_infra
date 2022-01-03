package com.ian_no_1.basic_infra

import com.ian_no_1.basic_infra.ui.HahaViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class CoroutineTestWithRule {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun test(){
        val vm = HahaViewModel()
        vm.setHahaPropertyByMainCoroutine()
        assert(vm.hahaProperty == "haha")
    }

}