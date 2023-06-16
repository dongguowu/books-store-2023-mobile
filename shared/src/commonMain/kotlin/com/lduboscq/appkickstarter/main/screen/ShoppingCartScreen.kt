package com.lduboscq.appkickstarter.main.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.MyBottomBar
import com.lduboscq.appkickstarter.main.MyTopBar
import com.lduboscq.appkickstarter.main.Route


internal class ShoppingCartScreen(private var count: Int) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            // Show info or warning message on topBar
            topBar = { MyTopBar("$count", false, scrollBehavior) },

            // Show a shopping cart icon and the number of items on cart
            bottomBar = { MyBottomBar(count = count, currentScreen = Route.ShoppingCart(count)) },
        )
        {
            Text("There are $count books in your shopping cart")
        }
    }
}