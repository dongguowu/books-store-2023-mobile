@file:OptIn(ExperimentalResourceApi::class)

package com.lduboscq.appkickstarter.main.screen

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.lduboscq.appkickstarter.model.Book
import com.lduboscq.appkickstarter.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi


internal class BookStoreHomeScreen(var user: User? = null) : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {


        val bookList = listOf(
            Book(
                "ad",
                "head first kotlin",
                "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
            ),
            Book("ad", "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
            Book(
                "ad", "kotlin in action", "https://kotlinlang.org/docs/images/kotlin-in-action.png"
            ),
            Book(
                "ad",
                "head first kotlin",
                "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
            ),
            Book("ad", "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
            Book(
                "ad", "kotlin in action", "https://kotlinlang.org/docs/images/kotlin-in-action.png"
            ),
        )
        var welcomeString = "welcome!"
        if (user != null) {
            welcomeString = "hi, ${user?.name}"
        }
        var infoText by remember { mutableStateOf(welcomeString) }
        var numberOfItems by remember { mutableStateOf(0) }
        fun addToCart(count: Int = 1) {
            numberOfItems += count
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        // Layout
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { MyTopBar(infoText, false, scrollBehavior) },

            bottomBar = { MyBottomBar(count = numberOfItems, currentScreen = Route.Home(user)) },

            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 9.dp)
                        .fillMaxSize(),
                ) {
                    SearchBook(updateInfo = { infoText = it }, updateQueryString = {})
                    BooksLazyColumn(bookList = bookList, addToCart = { addToCart() })
                }
            }

        )

    }
}

@Composable
fun SearchBook(updateQueryString: (String) -> Unit, updateInfo: (String) -> Unit) {
    var string = ""
    TextField(
        value = string,
        onValueChange = {
            updateQueryString(it)
            updateInfo("Found book related $it")
        },
        singleLine = true,
        label = { Text("Enter book's title") },
        shape = MaterialTheme.shapes.large
    )
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

                }
            }
        }
    }
}

