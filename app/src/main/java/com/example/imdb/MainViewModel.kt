package com.example.imdb


import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.imdb.models.MovieResponse
import com.example.imdb.network.ApiResult
import kotlinx.coroutines.launch

class MainViewModel(val handler:SavedStateHandle,val imdbRepository:ImdbRepository) : ViewModel(){
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