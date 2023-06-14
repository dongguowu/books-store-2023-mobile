package com.lduboscq.appkickstarter.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable


@Composable
fun MyTopBar(message: String, isErrorMessage: Boolean = false) {
    if (message.isNotEmpty()) {
        if (isErrorMessage) {
            TopAppBar(
                title = { Text(text = message) },
                backgroundColor = MaterialTheme.colors.error,
                contentColor = MaterialTheme.colors.onError
            )
        } else {
            TopAppBar(
                title = { Text(text = message) },
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}