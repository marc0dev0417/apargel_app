package com.example.spash_apargel.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
  @SerializedName("id") val id: UUID? = null,
  @SerializedName("username") val username: String ? = null,
  @SerializedName("password") val password: String ? = null
)


