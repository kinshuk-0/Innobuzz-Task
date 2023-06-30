package com.example.innobuzztask.network

import com.example.innobuzztask.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    fun getPosts(): Call<PostResponse>
}