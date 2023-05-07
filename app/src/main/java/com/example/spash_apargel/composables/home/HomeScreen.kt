package com.example.spash_apargel.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spash_apargel.mvvm.ApargelViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(apargelViewModel: ApargelViewModel, navController: NavController){
        val pos = LatLng(41.935803, -0.757593)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pos, 6f)
        }

        Box(modifier = Modifier.fillMaxSize()) { // Usamos el composable Box para colocar el mapa encima de la TopAppBar
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = pos),
                    title = "Spain"
                )
            }
            TopAppBar(
                modifier = Modifier
                    .background(Color(145, 203, 255))
                    .height(50.dp), // Alinear la TopAppBar en la parte superior central de la pantalla
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                title = { Text(text = "") },

                )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                Arrangement.SpaceBetween
            ) {
                Search()
                MyUI(apargelViewModel = apargelViewModel, navController = navController)

            }
        }
}

@Composable
fun MyUI(navController: NavController, apargelViewModel: ApargelViewModel) {
    val listItems = arrayOf("Tickets", "Aministrador")
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }


    // Dialog state Manager
    val dialogStateTicket: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val dialogStateAdministrator: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    // Code to Show and Dismiss Dialog
    if (dialogStateTicket.value) {
        AlertTickets(dialogState = dialogStateTicket, navController)
    }
    if (dialogStateAdministrator.value) {
        AlertAdministrator(apargelViewModel, dialogState = dialogStateAdministrator, navController)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        // 3 vertical dots icon
        IconButton(onClick = {
            expanded = true
        },
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .size(30.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
                tint = Color.Black
            )
        }

        // drop down menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            listItems.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        if (itemIndex == 0){
                            dialogStateTicket.value = true
                        } else {
                            dialogStateAdministrator.value = true
                        }
                        expanded = false
                    },
                ) {
                    Text(text = itemValue)
                }
            }
        }
    }
}

@Composable
fun Search() {
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
        placeholder = { Text("Buscar") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar"
            )
        },
    )
}

@Composable
fun AlertTickets(dialogState: MutableState<Boolean>, navController: NavController){
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        title = {
            Text(text = "Tickets")
        },
        text = {
            Column() {
                OutlinedTextField(value = name,
                    onValueChange = { name = it},
                    label = { Text(text = "Nombre") }
                )
                OutlinedTextField(value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Descripcion") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dialogState.value = false

                }
            ) {
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialogState.value = false
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AlertAdministrator(
    apargelViewModel: ApargelViewModel,
    dialogState: MutableState<Boolean>,
    navController: NavController
){
    val currentContext = LocalContext.current.applicationContext
   val username: String by apargelViewModel.username.observeAsState(initial = "")
   val password: String by apargelViewModel.password.observeAsState(initial = "")
   val isLoading: Boolean by apargelViewModel.isLoading.observeAsState(initial = false)

    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        title = {
            Text(text = "Login")
        },
        text = {
            Column {
                OutlinedTextField(value = username,
                    onValueChange = { apargelViewModel.changeUsername(it)},
                    label = { Text(text = "Usuario") }
                )
                OutlinedTextField(value = password,
                    onValueChange = { apargelViewModel.changePassword(it) },
                    label = { Text(text = "Contraseña") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { apargelViewModel.onLoginSelected(currentContext, navController) }
            ) {
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialogState.value = false
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}
