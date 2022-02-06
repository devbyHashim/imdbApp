package com.example.imdb

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imdb.models.ImdbConfiguration
import com.example.imdb.models.Movie
import com.example.imdb.models.MovieResponse
import com.example.imdb.network.ApiResult
import com.example.imdb.network.ApiService
import com.example.imdb.network.NetworkManager
import kotlinx.coroutines.*
import retrofit2.await
import timber.log.Timber


object ImdbRepository{
    const val KEY = "83d01f18538cb7a275147492f84c3698"
    lateinit var imdbApp : ImdbApp
    private val apiService by lazy {
        NetworkManager.apiService
    }
    val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.v(throwable)
    }
    lateinit var configuration:ImdbConfiguration
    private val repositoryScope = CoroutineScope(handler +
            Dispatchers.IO +
            SupervisorJob())

    init {
       repositoryScope.launch {
          configuration = apiService.configuration(KEY).await()
           Timber.v("got response for configuration")
       }
    }

    fun queryResult(input: String): LiveData<ApiResult<MovieResponse>> {
        val liveData = MutableLiveData<ApiResult<MovieResponse>>()
        liveData.value = ApiResult.Loading()
        repositoryScope.launch {
            val response = apiService.searchMovies(KEY,input)
                .execute()
            if (response.isSuccessful && response.code()==200 && response.body()!=null){
                liveData.postValue(ApiResult.Success(response.body()!!))
            }
            else{
                liveData.postValue(ApiResult.Error(Throwable("issue in api")))
            }
        }
        return liveData
    }

    fun movieDetails(path: String): LiveData<ApiResult<Movie>> {
        val liveData = MutableLiveData<ApiResult<Movie>>()
        liveData.value = ApiResult.Loading()
        repositoryScope.launch {
            val response = apiService.details(path,KEY)
                .execute()
            if (response.isSuccessful && response.code()==200 && response.body()!=null){
                liveData.postValue(ApiResult.Success(response.body()!!))
            }
            else{
                liveData.postValue(ApiResult.Error(Throwable("issue in api")))
            }
        }
        return liveData
    }



}