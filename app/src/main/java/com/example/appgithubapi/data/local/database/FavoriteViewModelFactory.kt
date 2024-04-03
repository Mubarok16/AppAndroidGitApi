package com.example.appgithubapi.data.local.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appgithubapi.data.local.datastore.SettingPreference
import com.example.appgithubapi.ui.FavoriteViewModel

class FavoriteViewModelFactory(private val database: FavoriteRoomDatabase): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(database) as T
        }

        throw IllegalArgumentException("Unknown View Model Class: "+ modelClass.name)
    }

}