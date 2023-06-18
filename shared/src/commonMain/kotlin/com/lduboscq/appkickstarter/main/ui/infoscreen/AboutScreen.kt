package com.lduboscq.appkickstarter.main.ui.infoscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.Route
import com.lduboscq.appkickstarter.main.ui.MyBottomBar


internal class AboutScreen(var message: String) : Screen {

    @Composable
    override fun Content() {
        val quantity = 0

        Scaffold(
            topBar = { },
            bottomBar = {
                MyBottomBar(
                    quantity = quantity,
                    currentScreen = Route.About(quantity),
                    showCart = false
                )
            },

            content = { paddingValues ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(paddingValues),
                ) {
                    androidx.compose.material3.Text(
                        text = "message",
                        modifier = Modifier.padding(6.dp, 0.dp, 6.dp, 0.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
        )
    }
}