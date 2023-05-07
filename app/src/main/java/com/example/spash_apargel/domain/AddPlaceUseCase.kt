package com.example.spash_apargel.domain

import com.example.spash_apargel.models.Place
import com.example.spash_apargel.repository.ApargelRepository
import javax.inject.Inject

class AddPlaceUseCase @Inject constructor(private val apargelRepository: ApargelRepository) {
    suspend operator fun invoke(place: Place): Place {
        return apargelRepository.addPlace(place)
    }
}