package com.lduboscq.appkickstarter.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lduboscq.appkickstarter.main.Route
import com.lduboscq.appkickstarter.main.screenRouter
import com.lduboscq.appkickstarter.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(quantity: Int, currentScreen: Route) {
    val navigator = LocalNavigator.currentOrThrow
    BottomAppBar(
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 36.dp,
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }
            IconButton(
                onClick = {
                    if (currentScreen !is Route.Home) {
                        val user = User("Dongguo")
                        navigator.push(screenRouter(Route.Home(user)))
                    }

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Back Home",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (currentScreen !is Route.ShoppingCart) {
                        navigator.push(screenRouter(Route.ShoppingCart(quantity)))
                    }
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                content = {
                    BadgedBox(
                        badge = {
                            if (quantity >= 1) {
                                Badge {
                                    Text(
                                        quantity.toString(),
                                        modifier = Modifier.semantics {
                                            contentDescription = "$quantity books in shopping cart"
                                        },
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }) {
                        Icon(
                            Icons.Outlined.ShoppingCart,
                            contentDescription = "Display the quantity of books in shopping cart"
                        )
                    }
                }

            )
        }
    )
}

