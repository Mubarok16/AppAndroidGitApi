package com.example.appgithubapi.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appgithubapi.ui.DetailActivity
import com.example.appgithubapi.ui.FavoriteActivity
import kotlin.concurrent.Volatile

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteRoomDatabase: RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object{
        @Volatile
        private var INSTACE: FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: DetailActivity): FavoriteRoomDatabase {
            if(INSTACE == null){
                synchronized(FavoriteRoomDatabase::class.java){
                    INSTACE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTACE as FavoriteRoomDatabase
        }

        @JvmStatic
        fun getDatabaseFav(context: FavoriteActivity): FavoriteRoomDatabase {
            if(INSTACE == null){
                synchronized(FavoriteRoomDatabase::class.java){
                    INSTACE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTACE as FavoriteRoomDatabase
        }

    }
}