package com.example.practicaltest.data.network

import com.example.practicaltest.data.db.GitUser
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitApiCall {


@GET("users?since=135")
   suspend fun getUserList() : Response<ArrayList<GitUser>>

companion object{
    operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : RetrofitApiCall{
        val okHttpclient = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpclient)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApiCall :: class.java)
    }
}

}