package com.aviraldg.trackshack.api

import com.aviraldg.trackshack.api.models.AuthToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TrackShackApi {
    /** Authentication **/

    @FormUrlEncoded
    @POST("auth/login/")
    fun login(@Field("email") email: String, @Field("password") password: String): Call<AuthToken>
}