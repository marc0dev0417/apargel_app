package com.example.spash_apargel.composables.place


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spash_apargel.mvvm.ApargelViewModel
import com.example.spash_apargel.navigation.AppScreens
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun NewPlace(
    apargelViewModel: ApargelViewModel,
    navController: NavController
) {
    val currentContext = LocalContext.current
    val latitude: Double by apargelViewModel.latitude.observeAsState(initial = 41.935803)
    val longitude: Double by apargelViewModel.longitude.observeAsState(initial = -0.757593)
    val locality: String by apargelViewModel.locality.observeAsState(initial = "")
    val street: String by apargelViewModel.street.observeAsState(initial = "")

    val pos = LatLng(latitude, longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 6f)
    }

    Scaffold(
        topBar = {TopAppBar(
            modifier = Modifier
                .background(Color(145, 203, 255))
                .height(50.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = { Text(text = "Crear Nueva Plaza") },
            actions = {
                Row(Modifier.padding(end = 8.dp)) {
                    IconButton(
                        onClick = { navController.navigate(route = AppScreens.AdminScreen.route)},
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Volver atras"
                        )
                    }
                }
            }
        )}
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoogleMap(
                    onMapClick = { coordinate ->
                        apargelViewModel.changeLatitude(coordinate.latitude)
                        apargelViewModel.changeLongitude(coordinate.longitude)
                                 },
                    modifier = Modifier.height(400.dp),
                    cameraPositionState = cameraPositionState
                ){
                    Marker(
                        state = MarkerState(position = pos),
                        title = "Spain"
                    )
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = locality,
                    onValueChange = { value -> apargelViewModel.changeLocality(value) },
                    label = { Text(text = "Localidad") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = street,
                    onValueChange = { value -> apargelViewModel.changeStreet(value) },
                    label = { Text(text = "Calle") }
                )
                Box(contentAlignment = Alignment.Center) {
                    Button(onClick = { apargelViewModel.addPlace(currentContext, navController) }) {
                        Text(text = "Añadir")
                    }
                }

            }
        }
    }

}