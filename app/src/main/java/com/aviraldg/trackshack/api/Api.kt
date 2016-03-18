package com.aviraldg.trackshack.api

import android.util.Log
import com.aviraldg.trackshack.BuildConfig
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

var apiKey: String? = null

private val API_URL = "http://trackshack-app.herokuapp.com/api/"

private val logInterceptor = okhttp3.logging.HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val interceptor = Interceptor {
    val req = it.request()

    var newReq = req.newBuilder()
            .addHeader("User-Agent", "${BuildConfig.APPLICATION_ID}; ${BuildConfig.VERSION_CODE}")
            .method(req.method(), req.body())
            .build()

    Log.i("Api.interceptor", "apiKey: $apiKey")

    if (apiKey != null) {
        newReq = newReq.newBuilder()
                .addHeader("Authorization", "Token $apiKey")
                .method(req.method(), req.body())
                .build()
    }

    it.proceed(newReq)
}

private val client = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .addInterceptor(interceptor)
        .build()

private val mapper: com.fasterxml.jackson.databind.ObjectMapper
    get() {
        val m = ObjectMapper()
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        m.propertyNamingStrategy = PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
        return m
    }

private val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(client)
        .addConverterFactory(retrofit2.converter.jackson.JacksonConverterFactory.create(mapper))
        .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
        .build()

val trackshackApi = retrofit.create(TrackShackApi::class.java)