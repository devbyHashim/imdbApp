package com.example.imdb

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

fun AppCompatActivity.factory(injector: ViewModelInjector):ViewModelProvider.Factory{
    return SaveStateViewModelFactory(this,injector)
}

fun Fragment.factory(injector: ViewModelInjector):ViewModelProvider.Factory{
    return SaveStateViewModelFactory(this,injector)
}