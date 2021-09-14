package com.ian_no_1.basic_infra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ian_no_1.basic_infra.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tryMe.setOnClickListener{}
    }


}