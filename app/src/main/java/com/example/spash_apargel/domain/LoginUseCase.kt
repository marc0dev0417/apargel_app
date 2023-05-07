package com.example.spash_apargel.domain

import com.example.spash_apargel.repository.ApargelRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val apargelRepository: ApargelRepository) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return apargelRepository.doLogin(username, password)
    }
}