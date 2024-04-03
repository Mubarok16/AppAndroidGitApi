package com.example.appgithubapi.data.local.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appgithubapi.ui.SettingViewModel

class ViewModelFactory(private val preference: SettingPreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(preference) as T
        }

        throw IllegalArgumentException("Unknown View Model Class: "+ modelClass.name)
    }

}