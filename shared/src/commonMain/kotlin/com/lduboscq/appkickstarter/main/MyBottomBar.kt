package com.lduboscq.appkickstarter.main

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun MyBottomBar(numberOfItems: Int) {
    BottomAppBar() {
        val navigator = LocalNavigator.currentOrThrow
        BottomAppBar {
            IconButton(
                onClick = {
                    navigator.push(screenRouter(Route.ShoppingCart(numberOfItems)))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "shopping cart"
                )
            }

            Text(
                text = numberOfItems.toString()
            )
        }
    }
}