package com.lduboscq.appkickstarter.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable


@Composable
fun MyTopBar(message: String, isErrorMessage: Boolean = false) {
    TopAppBar {
        if (isErrorMessage) {
            if(message.isNotEmpty()){
                TopAppBar(
                    title = { Text(text = message) },
                    backgroundColor = MaterialTheme.colors.error,
                    contentColor = MaterialTheme.colors.onError
                )
            }
        }else{
            if (message.isNotEmpty()) {
                TopAppBar(
                    title = { Text(text = message) },
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}