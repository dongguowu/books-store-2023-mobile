package com.lduboscq.appkickstarter.main.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.MyBottomBar
import com.lduboscq.appkickstarter.main.MyTopBar
import com.lduboscq.appkickstarter.main.Route


internal class ShoppingCartScreen(private var count: Int) : Screen {

    @Composable
    override fun Content() {
        Scaffold(
            // Show info or warning message on topBar
            topBar = { MyTopBar("", false) },

            // Show a shopping cart icon and the number of items on cart
            bottomBar = { MyBottomBar(count = count, currentScreen = Route.ShoppingCart(count)) },
        )
        {
            Text("There are $count books in your shopping cart")
        }
    }
}