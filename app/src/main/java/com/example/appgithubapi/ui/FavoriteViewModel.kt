package com.example.appgithubapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubapi.data.local.database.Favorite
import com.example.appgithubapi.data.local.database.FavoriteRoomDatabase

class FavoriteViewModel(private val data: FavoriteRoomDatabase): ViewModel() {

    fun getFavorite(): LiveData<List<Favorite>>{
        var fav = data.favDao().getAllFavorite()
        return fav
    }

}

