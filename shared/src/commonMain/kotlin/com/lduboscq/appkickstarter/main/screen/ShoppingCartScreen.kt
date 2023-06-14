package com.lduboscq.appkickstarter.main.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Book


internal class ShoppingCartScreen(var count: Int) : Screen {

    @Composable
    override fun Content() {
        Text("There are ${count.toString()} books in your shopping cart")
    }
}