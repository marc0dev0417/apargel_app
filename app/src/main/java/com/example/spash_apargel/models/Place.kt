package com.example.spash_apargel.models

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Place(
  @SerializedName("id") val id: UUID ? = null,
  @SerializedName("street") val street: String ? = null,
  @SerializedName("locality") val locality: String ? = null,
  @SerializedName("longitude") val longitude: Double ? = null,
  @SerializedName("latitude") val latitude: Double ? = null
)