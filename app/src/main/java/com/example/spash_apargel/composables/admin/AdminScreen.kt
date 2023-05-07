package com.example.spash_apargel.composables.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spash_apargel.models.Place
import com.example.spash_apargel.mvvm.ApargelViewModel
import com.example.spash_apargel.navigation.AppScreens

@Composable
fun AdminScreen(
    apargelViewModel: ApargelViewModel,
    navController: NavController
) {
    val placeList: List<Place> by apargelViewModel.placeList.observeAsState(initial = listOf())
   // val plazas = listOf("Ciudad de México", "Nueva York", "Tokio", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres", "Londres")
    Box(modifier = Modifier.height(50.dp)) {
        TopAppBar(
            modifier = Modifier
                .background(Color(145, 203, 255))
                .height(50.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = { Text(text = "Administrador") },
            actions = {
                Row(Modifier.padding(end = 8.dp)) {
                    IconButton(
                        onClick = { navController.navigate(route = AppScreens.MainScreen.route)},

                        ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Cerrar Sesion"
                        )
                    }
                }
            }
            )
    }
    Box(modifier = Modifier.padding(top = 50.dp)){
        AdminSearch()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), 
            contentPadding = PaddingValues(5.dp)
        )
        {
            items(placeList) {place ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    Card(
                        backgroundColor = Color(205, 230, 255),
                        elevation = 10.dp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Text(text = place.street!!)
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(route = AppScreens.NewPlace.route) },
            backgroundColor = Color(145, 203, 255),
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar"
            )
        }

    }
}

@Composable
fun AdminSearch() {
    var text by remember { mutableStateOf("") }

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            textColor = Color.Black,
            leadingIconColor = Color.Gray,
            trailingIconColor = Color.Gray,
            errorCursorColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red,
        ),
        modifier = Modifier.background(color = Color.Transparent),
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        placeholder = { Text("Buscar Localidad") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar Localidad"
            )
        },
    )
}