package com.example.spash_apargel.mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.spash_apargel.domain.AddPlaceUseCase
import com.example.spash_apargel.domain.GetPlacesUseCase
import com.example.spash_apargel.domain.LoginUseCase
import com.example.spash_apargel.models.Place
import com.example.spash_apargel.navigation.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApargelViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getPlacesUseCase: GetPlacesUseCase,
    private val addPlaceUseCase: AddPlaceUseCase
): ViewModel() {
    // Login states ->
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Place state ->
    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList

    private val _locality = MutableLiveData<String>()
    val locality: LiveData<String> = _locality
    private val _street = MutableLiveData<String>()
    val street: LiveData<String> = _street

    // Coordinates state ->
    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude
    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> = _longitude

    // Change states in login ->
    fun changeUsername(username: String) {
        _username.value = username
    }
    fun changePassword(password: String) {
        _password.value = password
    }

    // Change states in place
    fun changeLocality(locality: String) {
        _locality.value = locality
    }
    fun changeStreet(street: String) {
        _street.value = street
    }

    //Change states in coordinates ->
    fun changeLatitude(latitude: Double) {
        _latitude.value = latitude
    }
    fun changeLongitude(longitude: Double) {
        _longitude.value = longitude
    }


    fun onLoginSelected(context: Context, navController: NavController){
        viewModelScope.launch {
            _isLoading.value = true

            if(username.value == null || password.value == null){
                Toast.makeText(context, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            }else {
                val result = loginUseCase(username.value!!, password.value!!)
                if (result){
                    navController.navigate(route = AppScreens.AdminScreen.route) { getPlaces() }
                } else {
                    Toast.makeText(context, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
            _isLoading.value = false
        }
    }
    fun getPlaces(){
        viewModelScope.launch {
            val result = getPlacesUseCase()
            _placeList.value = result
        }
    }
    fun addPlace(context: Context, navController: NavController) {
        viewModelScope.launch {
            if (locality.value == null || street.value == null){
                Toast.makeText(context, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            } else {
                val result = addPlaceUseCase(
                    Place(
                        id = null,
                        locality = locality.value,
                        street = street.value,
                        latitude = latitude.value,
                        longitude = longitude.value
                    )
                )
                if (result.id != null){
                    Toast.makeText(context, "Se ha añadido el lugar", Toast.LENGTH_SHORT).show()
                    navController.navigate(route = AppScreens.AdminScreen.route) { getPlaces() }
                }
            }
        }
    }
}