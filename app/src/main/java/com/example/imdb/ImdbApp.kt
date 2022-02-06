package com.example.imdb

import android.app.Application
import com.example.imdb.network.NetworkManager
import timber.log.Timber

class ImdbApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ImdbRepository.imdbApp = this
    }
}