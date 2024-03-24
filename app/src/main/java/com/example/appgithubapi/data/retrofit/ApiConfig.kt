package com.example.appgithubapi.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private fun instanceRetro():Retrofit{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

        fun getApiService(): ApiService {
            return instanceRetro().create(ApiService::class.java)
        }

        fun getDetailService():DetailService{
            return instanceRetro().create(DetailService::class.java)
        }

        fun getFollowerService():followerService{
            return instanceRetro().create(followerService::class.java)
        }

        fun getFollowingService():followingService{
            return instanceRetro().create(followingService::class.java)
        }
    }
}
