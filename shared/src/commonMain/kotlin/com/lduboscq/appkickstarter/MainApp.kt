package com.lduboscq.appkickstarter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.lduboscq.appkickstarter.main.view.BookStoreHomeScreen
import com.lduboscq.appkickstarter.ui.theme.green.GreenAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainApp() {
    GreenAppTheme {
        Navigator(BookStoreHomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

}
