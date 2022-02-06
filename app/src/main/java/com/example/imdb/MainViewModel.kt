package com.example.imdb


import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.imdb.models.Movie
import com.example.imdb.models.MovieResponse
import com.example.imdb.network.ApiResult
import kotlinx.coroutines.launch

class MainViewModel(val handler:SavedStateHandle,val imdbRepository:ImdbRepository) : ViewModel(){
    val selectedMovieId = handler.getLiveData<String>("id")
    val selectedMovie =  Transformations.switchMap(selectedMovieId,object :
        Function<String, LiveData<ApiResult<Movie>>> {
        override fun apply(input: String?): LiveData<ApiResult<Movie>> {
            if (!input.isNullOrEmpty()){
                return imdbRepository.movieDetails(input)
            }
            return MutableLiveData()
        }

    })
    val query = handler.getLiveData<String>("query")
    val response = Transformations.switchMap(query,object :
        Function<String, LiveData<ApiResult<MovieResponse>>> {
        override fun apply(input: String?): LiveData<ApiResult<MovieResponse>> {
            if (!input.isNullOrEmpty()){
                return imdbRepository.queryResult(input)
            }
            return MutableLiveData()
        }

    })
}