package com.lduboscq.appkickstarter.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lduboscq.appkickstarter.model.User

@Composable
fun MyBottomBar(count: Int, currentScreen: Route) {
    val navigator = LocalNavigator.currentOrThrow
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        content = {

            // Home Icon
            if (currentScreen !is Route.Home) {
                IconButton(
                    onClick = {
                        val user = User("Donggu")
                        navigator.push(screenRouter(Route.Home(user)))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Back Home"
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))
            // Shopping cart icon
            IconButton(
                onClick = {
                    navigator.push(screenRouter(Route.ShoppingCart(count)))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "shopping cart"
                )
            }

            Text(
                text = count.toString()
            )
        }
    )
}
