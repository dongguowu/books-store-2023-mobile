package com.lduboscq.appkickstarter.main.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
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
import com.lduboscq.appkickstarter.main.router.Route
import com.lduboscq.appkickstarter.main.router.screenRouter
import com.lduboscq.appkickstarter.main.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(quantity: Int, currentScreen: Route, showCart: Boolean = true) {
    val navigator = LocalNavigator.currentOrThrow
    BottomAppBar(contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 36.dp,

        actions = {
            IconButton(
                onClick = {
                    if (currentScreen !is Route.Home) {
                        val user = User("Dongguo")
                        navigator.push(screenRouter(Route.Home(user)))
                    }
                },
                enabled = currentScreen !is Route.Home
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Back Home",
                )
            }
            IconButton(
                onClick = {
                    if (currentScreen !is Route.About) {
                        val feature =
                            "## Feature\n" + "\n" + "- Model-View-ViewModel \n" + "- AppKickstarter template\n" + "- Voyager Screen/ScreenModel\n" + "- Mongo Realm Sync database\n" + "- Stateless components\n" + "- Material design\n" + "- Layouts consistency"
                        navigator.push(screenRouter(Route.About(feature)))
                    }
                },
                enabled = currentScreen !is Route.About
            ) {
                Icon(
                    Icons.Outlined.Build,
                    contentDescription = "go to About screen",
                )
            }
        },
        floatingActionButton = {
            if (showCart) {
                FloatingActionButton(onClick = {
                    if (currentScreen !is Route.ShoppingCart) {
                        navigator.push(screenRouter(Route.ShoppingCart(quantity)))
                    }
                },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    content = {
                        BadgedBox(badge = {
                            if (quantity >= 1) {
                                Badge {
                                    Text(
                                        quantity.toString(), modifier = Modifier.semantics {
                                            contentDescription = "$quantity books in shopping cart"
                                        }, fontSize = 16.sp
                                    )
                                }
                            }
                        }) {
                            Icon(
                                Icons.Outlined.ShoppingCart,
                                contentDescription = "Display the quantity of books in shopping cart"
                            )
                        }
                    })
            }
        })
}

