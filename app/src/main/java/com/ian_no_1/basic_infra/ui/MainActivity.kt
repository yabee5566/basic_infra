package com.ian_no_1.basic_infra.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ian_no_1.basic_infra.databinding.ActivityMainBinding
import com.ian_no_1.basic_infra.util.HahaManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var hahaManager1: HahaManager
    @Inject
    lateinit var hahaManager2: HahaManager
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tryMe.setOnClickListener {

            hahaManager1.sayHaha()
            hahaManager2.sayHaha()
          //  viewModel.haha()

        }
    }
}