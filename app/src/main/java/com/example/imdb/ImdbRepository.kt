package com.example.imdb

import android.content.Context
import com.example.imdb.network.ApiService
import com.example.imdb.network.NetworkManager


object ImdbRepository{
    lateinit var imdbApp : ImdbApp
    private val apiService by lazy {
        NetworkManager.apiService
    }




}