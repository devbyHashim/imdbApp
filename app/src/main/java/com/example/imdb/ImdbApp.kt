package com.example.imdb

import android.app.Application
import com.example.imdb.network.NetworkManager

class ImdbApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ImdbRepository.imdbApp = this
    }
}