package com.lduboscq.appkickstarter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.lduboscq.appkickstarter.main.shoppingcart.ShoppingCartScreen
import com.lduboscq.appkickstarter.ui.theme.green.GreenAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun MainApp() {
    GreenAppTheme {

//        Navigator(BookStoreHomeScreen()) { navigator ->
            Navigator(ShoppingCartScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

}
