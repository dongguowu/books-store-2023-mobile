package com.lduboscq.appkickstarter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            }
            Row {
                TextField(value = frogName, onValueChange = { frogName = it })
            }

            Row {

                Button(onClick = { screenModel.addFrog(frogName) }) {
                    Text("Add Frog")
                }
                Button(onClick = { screenModel.getFrog(frogName) }) {
                    Text("Get Frog")
                }
            }
            if (state is FrogScreenModel.State.Result) {
                var message = ""
                for (item in (state as FrogScreenModel.State.Result).frogList) {
                    message += "name: ${item.name}, \nid: ${item._id}\n\n"
                }
                Text(message)
            }
        }
    }
}