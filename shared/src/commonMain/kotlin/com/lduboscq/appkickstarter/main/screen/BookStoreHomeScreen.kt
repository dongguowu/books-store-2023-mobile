@file:OptIn(ExperimentalResourceApi::class)

package com.lduboscq.appkickstarter.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lduboscq.appkickstarter.main.Image
import com.lduboscq.appkickstarter.main.MyBottomBar
import com.lduboscq.appkickstarter.main.MyTopBar
import com.lduboscq.appkickstarter.main.Route
import com.lduboscq.appkickstarter.main.screenRouter
import com.lduboscq.appkickstarter.model.User
import model.Book
import org.jetbrains.compose.resources.ExperimentalResourceApi


internal class BookStoreHomeScreen(var user: User? = null) : Screen {

    @Composable
    override fun Content() {
//
//@Composable
//fun MainScreen() {
//        AppTheme {

        // Global Variables

//        Log.v("Successfully opened an in memory realm")


        val bookList = listOf(
            Book(
                1, "head first kotlin", "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
            ),
            Book(2, "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
            Book(
                3, "kotlin in action", "https://kotlinlang.org/docs/images/kotlin-in-action.png"
            ),
            Book(
                1, "head first kotlin", "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
            ),
            Book(2, "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
            Book(
                3, "kotlin in action", "https://kotlinlang.org/docs/images/kotlin-in-action.png"
            ),

            )
        var welcomeString = "welcome!"
        if (user != null) {
            welcomeString = "hi, ${user?.name}"
        }
        var infoText by remember { mutableStateOf(welcomeString) }
        var queryString by remember { mutableStateOf("") }
        var numberOfItems by remember { mutableStateOf(0) }
        fun addToCart(count: Int = 1) {
            numberOfItems += count
        }

        val navigator = LocalNavigator.currentOrThrow

        // Layout
        Scaffold(
            // Show info or warning message on topBar
            topBar = { MyTopBar(infoText, true) },

            // Show a shopping cart icon and the number of items on cart
            bottomBar = { MyBottomBar(count = numberOfItems, currentScreen = Route.Home(user)) },


            // Button
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(screenRouter(Route.About(numberOfItems)))
                    },
                ) {
                    Text("Click me")
                }
            }

            // end of Button
        ) {

            // Main body, display a search textField and a list of book cards
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 9.dp)
                    .background(color = MaterialTheme.colors.background).fillMaxSize(),
            ) {

                // Search
                TextField(
                    value = queryString,
                    onValueChange = {
                        queryString = it
                        infoText = if (queryString.length >= 3) {
                            "Found book related $queryString"
                        } else {
                            ""
                        }

                    },
                    singleLine = true,
                    label = { Text("Enter book's title") },
                    shape = MaterialTheme.shapes.large
                )

                BooksLazyColumn(bookList = bookList, addToCart = { addToCart() })

            }
        }
    }
}

@Composable
fun BooksLazyColumn(bookList: List<Book>, addToCart: () -> Unit) {
    LazyColumn {
        items(bookList) { book ->
            BookCard(book = book, addToCart = { addToCart() })
        }
        for (book in bookList) {
            item {
                BookCard(book = book, addToCart = {
                    addToCart()
                })
            }
        }
    }
}


/**

Represents a card component for displaying book information including title,
picture and favorite icon button , add to shopping cart icon button.
@param book The book object to display.
@param addToCart A callback function to handle adding the book to the shopping cart.
 */
@Composable
fun BookCard(book: Book, addToCart: () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier.size(width = 400.dp, height = 200.dp).padding(15.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Row {

            Image(
                url = book.imagePath,
                modifier = Modifier.size(width = 120.dp, height = 180.dp).padding(15.dp)
                    .clickable(onClick = {
                        navigator.push(screenRouter(Route.Detail(book)))
                    })
            )
            Column(
                modifier = Modifier.padding(9.dp, 15.dp, 9.dp, 9.dp),
            ) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSecondary,
                    textAlign = TextAlign.Start,
                )

                Spacer(modifier = Modifier.height(60.dp).width(60.dp))

                Row {
                    // Favorite icon
                    var favoriteIconTint by remember { mutableStateOf(Color.Gray) }
                    ExtendedFloatingActionButton(onClick = {
                        favoriteIconTint = if (favoriteIconTint == Color.Gray) {
                            Color.Red
                        } else {
                            Color.Gray
                        }
                    }, backgroundColor = MaterialTheme.colors.primary, icon = {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = favoriteIconTint
                        )
                    }, text = { Text("") })

                    Spacer(modifier = Modifier.width(20.dp))
                    // Add to shopping cart icon
                    ExtendedFloatingActionButton(onClick = {
                        addToCart()
                    }, backgroundColor = MaterialTheme.colors.primary, icon = {
                        Icon(
                            Icons.Filled.Add, contentDescription = "Add to " + "shopping cart"
                        )
                    }, text = { Text("") })
                }
            }
        }
    }
}

