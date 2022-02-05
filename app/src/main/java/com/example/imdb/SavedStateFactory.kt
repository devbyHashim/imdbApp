package com.example.imdb

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
interface SavedStateViewModelInjector<T : ViewModel> {

    fun create (handler : SavedStateHandle):T
}

typealias SvFactory = SaveStateViewModelFactory
typealias ViewModelInjector = SavedStateViewModelInjector<*>

class SaveStateViewModelFactory (savedStateRegistryOwner:  SavedStateRegistryOwner,
                                 private val savedStateViewModelInjector: SavedStateViewModelInjector<*>)
    :AbstractSavedStateViewModelFactory(savedStateRegistryOwner,null){

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return savedStateViewModelInjector.create(handle) as T
    }


}

class MainViewModelInjector:SavedStateViewModelInjector<MainViewModel>{
    override fun create(handler: SavedStateHandle): MainViewModel = MainViewModel(handler,ImdbRepository)

}