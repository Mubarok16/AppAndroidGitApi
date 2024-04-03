package com.example.appgithubapi.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM Favorite ORDER BY id DESC")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE name = :name AND favoritebtn = 1")
    fun getFavorite(name: String): List<Favorite>

    @Query("DELETE FROM Favorite WHERE name = :name")
    fun deleteFav(name: String)
}