package com.example.spash_apargel.models.dto

import com.google.gson.annotations.SerializedName

data class UserLoginDTO(
    @SerializedName("success") val success: Boolean ? = null
)
