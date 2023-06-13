package com.lduboscq.appkickstarter.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Composable
fun MainScreen() {

    Scaffold(
        topBar = { TopAppBar { Text("my app") } },
        bottomBar = { BottomAppBar { Text("Dongguo") } }) {
        MainContent()
    }
}

@Composable
fun MainContent() {
    Column {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.h4
        )
    }
    DisPlayList()

}

@Composable
fun DisPlayList() {
    var count by remember { mutableStateOf(1) }
    var showImage by remember { mutableStateOf(true) }
//    ImageCard(showImage = showImage, setShowImage = { showImage = it }, count = count, setCount = { count = it })
}

@OptIn(ExperimentalResourceApi::class)
@Composable
//fun ImageCard(showImage: Boolean, setShowImage: (Boolean) -> Unit, count: Int, setCount: (Int) -> Unit) {
//    Card() {
//        if (showImage) {
//            Image(
//                painter = painterResource("kotlin-in-action.png"),
//                contentDescription = null,
//                modifier = Modifier.clickable(onClick = {
//                    setCount
//                })
//            )
//        }
//
//
//    }
//
//
//}
fun InputExercises() {
    var infoText by remember { mutableStateOf("an infor message") }

    // password
    var passwordValue by remember { mutableStateOf("") }
    TextField(
        value = passwordValue,
        onValueChange = {
            passwordValue = it
            infoText = it
        },
        textStyle = TextStyle(textAlign = TextAlign.Center),
        label = { Text(text = "Please enter your password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )

    // Email
    var emailValue by remember { mutableStateOf("") }
    TextField(
        value = emailValue,
        onValueChange = {
            emailValue = it
            infoText = it
        },
        textStyle = TextStyle(textAlign = TextAlign.Start),
        label = { Text(text = "Please enter your email") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        )
    )

    // Number
    var ageValue by remember { mutableStateOf(100) }
    TextField(
        value = ageValue.toString(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            if (it.length == 4) {
                val currentValue = it.toInt()
                if (currentValue in 1900..2023) {
                    ageValue = currentValue
                }
            }
        },
        textStyle = TextStyle(textAlign = TextAlign.Start),
        label = { Text(text = "Please enter your age") },
    )
}