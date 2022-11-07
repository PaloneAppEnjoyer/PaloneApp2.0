package com.palone.paloneapp.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            return@Interceptor chain.proceed(builder.build())
        }).addInterceptor(logging)
    }.followRedirects(false).build()
    val api: OnlineResourcesApi by lazy {
        Retrofit.Builder().baseUrl("https://elektryk.edupage.org")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            .create(OnlineResourcesApi::class.java)
    }
}