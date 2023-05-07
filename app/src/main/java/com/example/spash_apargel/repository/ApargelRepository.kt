package com.example.spash_apargel.repository

import com.example.spash_apargel.models.Place
import com.example.spash_apargel.service.ApargelService
import javax.inject.Inject

class ApargelRepository @Inject constructor(private val apargelService: ApargelService){

    suspend fun doLogin(username: String, password: String): Boolean {
       return apargelService.doLogin(username, password)
    }
    suspend fun getPlaces(): List<Place> {
        return apargelService.getPlaces()
    }
    suspend fun addPlace(place: Place): Place {
        return apargelService.addPlace(place)
    }
}