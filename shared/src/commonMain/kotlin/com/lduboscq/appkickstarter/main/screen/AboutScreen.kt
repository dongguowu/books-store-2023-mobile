package com.lduboscq.appkickstarter.main.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen


internal class AboutScreen(var message: String) : Screen {

    @Composable
    override fun Content() {
        Text(message)
    }
}