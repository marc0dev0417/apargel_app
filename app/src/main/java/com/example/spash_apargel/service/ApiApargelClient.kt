package com.example.spash_apargel.service

import com.example.spash_apargel.models.Place
import com.example.spash_apargel.models.dto.UserLoginDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiApargelClient {
    @GET("users/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<UserLoginDTO>

    @GET("places")
    suspend fun getPlaces(): Response<List<Place>>

    @POST("places")
    suspend fun addPlace(@Body place: Place): Response<Place>
}