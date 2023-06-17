package com.lduboscq.appkickstarter.main.book

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    message: String,
    isErrorMessage: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior
) {
    if (message.isNotEmpty()) {
        if (isErrorMessage) {
            TopAppBar(
                title = { Text(text = message) },
                backgroundColor = MaterialTheme.colors.error,
                contentColor = MaterialTheme.colors.onError

            )
        } else {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = message) },
            )
        }
    }
}