package com.example.spash_apargel.service

import com.example.spash_apargel.models.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApargelService @Inject constructor(
    private val apiApargelClient: ApiApargelClient,
    ) {
    suspend fun doLogin(username: String, password: String): Boolean {
     return withContext(Dispatchers.IO) {
         val response = apiApargelClient.login(username, password)
         response.body()?.success?: false
        }
    }
    suspend fun getPlaces(): List<Place> {
        return withContext(Dispatchers.IO) {
            val response = apiApargelClient.getPlaces()
            response.body()!!
        }
    }
    suspend fun addPlace(place: Place): Place {
        return withContext(Dispatchers.IO) {
            val response = apiApargelClient.addPlace(place)
            response.body()!!
        }
    }
}