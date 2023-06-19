package com.lduboscq.appkickstarter.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.router.Route
import com.lduboscq.appkickstarter.main.layout.MyBottomBar

internal class AboutScreen(var feature: String) : Screen {

    @Composable
    override fun Content() {
        val quantity = 0

        Scaffold(
            topBar = { },
            bottomBar = {
                MyBottomBar(
                    quantity = quantity,
                    currentScreen = Route.About(""),
                    showCart = false
                )
            },

            content = { paddingValues ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {

                    Box(
                        Modifier.fillMaxSize()
                    ) {

                        Image(
                            url = "https://as1.ftcdn.net/v2/jpg/03/16/86/98/1000_F_316869849_oHD2qOXRJlZE2Md6SNv0MtvDcgZyHkfv.jpg",
                            modifier = Modifier.fillMaxSize()
                        )
                        var array = feature.split("\n")
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 200.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            for (text in array) {
                                item {
                                    Text(
                                        text = text,
                                        modifier = Modifier.padding(6.dp, 0.dp, 6.dp, 0.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        lineHeight = 120.sp,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}