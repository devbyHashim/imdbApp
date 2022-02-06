package com.example.imdb.network

import com.example.imdb.BASE_URL
import com.example.imdb.models.ImdbConfiguration
import com.example.imdb.models.MovieResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkManager {
    val apiService by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        retrofit.create(ApiService::class.java)
    }


}

interface ApiService{

    @GET("/3/search/movie")
    fun searchMovies(@Query("api_key")api_key:String,
                     @Query("query")query:String):Call<MovieResponse>

    @GET("/3/configuration")
    fun configuration(@Query("api_key")api_key:String):Call<ImdbConfiguration>

}

sealed class ApiResult<out T>{
   data class Error(val error:Throwable):ApiResult<Nothing>()
    data class Success<T>(val data:T):ApiResult<T>()
    class Loading:ApiResult<Nothing>()
}