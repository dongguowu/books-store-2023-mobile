package com.lduboscq.appkickstarter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class FrogScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel() { FrogScreenModel(FrogRepositoryRemote()) }
        val state by screenModel.state.collectAsState()

        var frogName by remember { mutableStateOf("") }

        Column {
            when (val result = state) {
                is FrogScreenModel.State.Init -> Text("Just initialized")
                is FrogScreenModel.State.Loading -> Text("Loading")
                is FrogScreenModel.State.Result -> {
                    Text("Success")
                }

                else -> {}
            }
            TextField(value = frogName, onValueChange = { frogName = it })
            Text("Please enter the name of the frog to add/get/update/delete")

            Row {

                Button(onClick = { screenModel.addFrog(frogName) }) {
                    Text("Add Frog")
                }

                Spacer(modifier = Modifier.width(9.dp))
                Button(onClick = { screenModel.getFrog(frogName) }) {
                    Text("Get Frog")
                }
            }
            if (state is FrogScreenModel.State.Result) {

                for (frog in (state as FrogScreenModel.State.Result).frogList) {
                    Button(onClick = {
                        screenModel.deleteFrog(frog._id)
                    }) {
                        Column {
                            Text("name: ${frog.name}, \nid: ${frog._id}")
                            Text("age: ${frog?.age?.toString()}")
                            Text("Species: ${frog?.species}")
                            Text("Owner: ${frog?.owner}")
                        }

                    }
                    Spacer(modifier = Modifier.height(9.dp))

                }
            }
        }
    }
}