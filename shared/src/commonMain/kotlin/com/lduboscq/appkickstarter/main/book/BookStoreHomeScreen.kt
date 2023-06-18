@file:OptIn(ExperimentalResourceApi::class)

package com.lduboscq.appkickstarter.main.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lduboscq.appkickstarter.main.data.CartLine
import com.lduboscq.appkickstarter.FrogRepositoryRemote
import com.lduboscq.appkickstarter.FrogScreenModel
import com.lduboscq.appkickstarter.main.Image
import com.lduboscq.appkickstarter.model.BookData
import com.lduboscq.appkickstarter.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi


internal class BookStoreHomeScreen(var user: User? = null) : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel() { FrogScreenModel(FrogRepositoryRemote()) }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(true) {
            screenModel.getFrog("")
        }
        var message by remember { mutableStateOf("") }
        when (val result = state) {
            is FrogScreenModel.State.Init -> message = "Just initialized"
            is FrogScreenModel.State.Loading -> message = "Loading"
            is FrogScreenModel.State.Result -> message = "Success"
            else -> {}
        }

        val bookList = getBookList()

        val cartLineList = remember { mutableListOf<CartLine>() }
        var quantity by remember { mutableStateOf(cartLineList.size) }
        if (state is FrogScreenModel.State.Result) {
            quantity = (state as FrogScreenModel.State.Result).cartLineList.sumOf { frog -> frog.quantity }
        }

        var welcomeString = "welcome!"
        if (user != null) {
            welcomeString = "hi, ${user?.name}"
        }
        var infoText by remember { mutableStateOf(welcomeString) }

        fun addToCart(book: BookData) {
            screenModel.addFrog(book.id)
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        // Layout
        Scaffold(
            topBar = { MyTopBar(infoText, false, scrollBehavior) },

            bottomBar = {
                MyBottomBar(
                    quantity = quantity,
                    currentScreen = Route.Home(user)
                )
            },

            content = { paddingValues ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(paddingValues),
                ) {
                    SearchBook(updateInfo = { infoText = it }, updateQueryString = {})
                    BooksLazyColumn(bookList = bookList, addToCart = { addToCart(it) })
                }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            backgroundColor = MaterialTheme.colorScheme.background
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
fun BooksLazyColumn(bookList: List<BookData>, addToCart: (book: BookData) -> Unit) {
    LazyColumn {

        var i = 2;
        for (book in bookList) {
            item {
                var n = i++ % 3
                when (n) {
                    0 -> {
                        BookCard(
                            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            book = book,
                            addToCart = {
                                addToCart(book)
                            })
                    }

                    1 -> {
                        BookCard(
                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                            book = book,
                            addToCart = {
                                addToCart(book)
                            })
                    }

                    2 -> {
                        BookCard(
                            textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                            book = book,
                            addToCart = {
                                addToCart(book)
                            })
                    }
                }
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
fun BookCard(
    textColor: Color,
    backgroundColor: Color,
    book: BookData,
    addToCart: (book: BookData) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier.size(width = 400.dp, height = 200.dp).padding(15.dp),
        backgroundColor = backgroundColor
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
                    color = textColor,
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
                    }, backgroundColor = MaterialTheme.colorScheme.primary, icon = {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = favoriteIconTint
                        )
                    }, text = { Text("") })

                    // add shopping icon
                    ExtendedFloatingActionButton(onClick = {
                        addToCart(book)
                    }, backgroundColor = MaterialTheme.colorScheme.primary, icon = {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add to shopping cart",
                        )
                    }, text = { Text("") })

                }
            }
        }
    }
}

