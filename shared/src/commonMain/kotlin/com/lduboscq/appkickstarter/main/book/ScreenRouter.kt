package com.lduboscq.appkickstarter.main.book

import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.screen.AboutScreen
import com.lduboscq.appkickstarter.main.screen.DetailScreen

fun screenRouter(screen: Route): Screen {
    return when (screen) {
        is Route.Home -> {
            BookStoreHomeScreen(user = screen.user)
        }

        is Route.About -> {
            if (screen.count == 0) {
                AboutScreen(message = "Welcome")
            } else {
                AboutScreen(message = "Welcome Back")
            }
        }

        is Route.Detail -> {
            DetailScreen(book = screen.book)
        }

        is Route.ShoppingCart -> {
            ShoppingCartScreen(list = screen.list)
        }
    }
}