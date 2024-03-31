package com.example.appgithubapi.data.retrofit

import com.example.appgithubapi.data.response.DataDetailResponse
import com.example.appgithubapi.data.response.DatagithubResponse
import com.example.appgithubapi.data.response.Follow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.*

    interface ApiService {
        @GET("search/users")
        @Headers("Authorization: token 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")
        fun getUsers(@Query("q") queryuser: String): Call<DatagithubResponse>
    }

    interface DetailService{
        @GET("users/{username}")
        @Headers("Authorization: token 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")
        fun getDetailUsers(
            @Path("username") username: String
        ): Call<DataDetailResponse>
    }

    interface followerService{
        @GET("users/{user}/followers")
        @Headers("Authorization: token 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")
        fun getFollowers(
            @Path("user") user: String
        ): Call<List<Follow>>

    }

    interface followingService {
        @GET("users/{user}/following")
        @Headers("Authorization: token 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")
        fun getFollowing(
            @Path("user") user: String
        ): Call<List<Follow>>
    }