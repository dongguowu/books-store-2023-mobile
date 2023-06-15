package com.lduboscq.appkickstarter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.lduboscq.appkickstarter.main.screen.BookStoreHomeScreen
import com.lduboscq.appkickstarter.ui.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun MainApp() {
    AppTheme {
//        Navigator(BookStoreHomeScreen()) { navigator ->
            Navigator(FrogScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

}
