package com.example.imdb

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.imdb.databinding.ActivityMainBinding

class MainActivity :AppCompatActivity(){

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
    }
    private val viewModel by lazy {
        val factory = factory(MainViewModelInjector())
        ViewModelProvider(this,factory)[MainViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
    }
}