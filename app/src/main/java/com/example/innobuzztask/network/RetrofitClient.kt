package com.example.innobuzztask.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val apiInterface: ApiInterface by lazy { buildRetrofitClient() }
    val baseUrl = "https://jsonplaceholder.typicode.com/posts/"

    val gson: Gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    private fun buildRetrofitClient() =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)

    private fun okHttpClient() = OkHttpClient.Builder().apply {
        connectTimeout(100, TimeUnit.SECONDS)
        writeTimeout(100, TimeUnit.SECONDS)
        readTimeout(100, TimeUnit.SECONDS)
    }.build()
}