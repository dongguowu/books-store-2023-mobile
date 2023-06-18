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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.lduboscq.appkickstarter.FrogRepositoryRemote
import com.lduboscq.appkickstarter.FrogScreenModel
import com.lduboscq.appkickstarter.main.Image
import com.lduboscq.appkickstarter.main.data.AddOrSubstrateQuantity
import com.lduboscq.appkickstarter.main.data.CartLine
import com.lduboscq.appkickstarter.main.data.CartLineData
import com.lduboscq.appkickstarter.model.BookData
import com.lduboscq.appkickstarter.model.User


internal class BookStoreHomeScreen(var user: User? = null) : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        // Insert shopping cart repository
        val screenModel = rememberScreenModel() { FrogScreenModel(FrogRepositoryRemote()) }
        val state by screenModel.state.collectAsState()


        // Local static books data
        val bookList = getBookList()


        // Message
        var messageOnTopBar by remember { mutableStateOf("") }
        when (val result = state) {
            is FrogScreenModel.State.Init -> messageOnTopBar = "Just initialized"
            is FrogScreenModel.State.Loading -> messageOnTopBar = "Loading"
            is FrogScreenModel.State.Result -> messageOnTopBar = "Success"
            else -> {}
        }

        // Load shopping cart data
        LaunchedEffect(true) {
            screenModel.getFrog("")
        }
        var quantity by remember { mutableStateOf(0) }
        if (state is FrogScreenModel.State.Result) {
            quantity =
                (state as FrogScreenModel.State.Result).cartLineList.sumOf { frog -> frog.quantity }
        }

        if (user != null) {
            messageOnTopBar = "hi, ${user?.name}"
        }


        // Layout - Scaffold
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = { MyTopBar(messageOnTopBar, scrollBehavior) },

            bottomBar = { MyBottomBar(quantity = quantity, currentScreen = Route.Home(user)) },

            content = { paddingValues ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(paddingValues),
                ) {
                    SearchBook(updateInfo = { messageOnTopBar = it }, updateQueryString = {})

                    LazyColumn {
                        for (book in bookList) {
                            item {
                                var cartLineList: List<CartLine>? = null
                                if (state is FrogScreenModel.State.Result) {
                                    cartLineList = (state as FrogScreenModel.State.Result).cartLineList
                                }
                                BookCard(
                                    book = book,
                                    cartLine = cartLineList?.firstOrNull { it.bookId == book.id },
                                    addToCartOrUpdate = { screenModel.addOrUpdateFrog(it) },
                                    removeFromCat = { screenModel.deleteFrog(it) })
                            }
                        }
                    }
                }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        )
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
    book: BookData,
    cartLine: CartLine?,
    addToCartOrUpdate: (cartLineData: CartLineData) -> Unit,
    removeFromCat: (cartLineId: String) -> Unit,
) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier.size(width = 400.dp, height = 200.dp).padding(15.dp),
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
                    textAlign = TextAlign.Start,
                )

                Spacer(modifier = Modifier.height(60.dp).width(60.dp))

                Row {
                    // Favorite icon
                    var checked by remember { mutableStateOf(false) }
                    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
                        if (checked) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    }


                    // add shopping icon
                    if (cartLine != null) {
                        AddOrSubstrateQuantity(cartLine = cartLine,
                            update = { addToCartOrUpdate(it) },
                            delete = { removeFromCat(it) })
                    } else {
                        ExtendedFloatingActionButton(onClick = {
                            addToCartOrUpdate(CartLineData(bookId = book.id, quantity = 1))
                        }, icon = {
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
}


@OptIn(ExperimentalMaterial3Api::class)
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