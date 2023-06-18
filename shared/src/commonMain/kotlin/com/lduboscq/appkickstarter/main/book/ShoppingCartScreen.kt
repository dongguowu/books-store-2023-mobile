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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.lduboscq.appkickstarter.main.Image

internal class ShoppingCartScreen(private var list: List<ShoppingCartLineData>) : Screen {
    private val bookList = getBookList()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel =
            rememberScreenModel { ShoppingCartScreenModel(ShoppingCartRepositoryRealmSync()) }
        val state by screenModel.state.collectAsState()

        var message by remember { mutableStateOf("") }
        when (val result = state) {
            is ShoppingCartScreenModel.State.Init -> message = "Just initialized"
            is ShoppingCartScreenModel.State.Loading -> message = "Loading"
            is ShoppingCartScreenModel.State.Result -> message = "Success"
            else -> {}
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            // Show info or warning message on topBar
            topBar = { MyTopBar(message, false, scrollBehavior) },

            // Show a shopping cart icon and the number of items on cart
            bottomBar = {
                MyBottomBar(
                    list = list,
                    totalQuantity = list.size,
                    currentScreen = Route.ShoppingCart(list)
                )
            },

            content = {
                Button(onClick = {
                    screenModel.getAll()
//                    var item = list[0]
//                    screenModel.add(item)
//                    screenModel.getAll()
                    for (cartLine in list) {
                        screenModel.add(cartLine)
                    }
                }) {
                    Text("add all cart line")
                }

                if (state is ShoppingCartScreenModel.State.Result) {

                    val cartLineList = mutableListOf<ShoppingCartLineData>()
                    for (carline in (state as ShoppingCartScreenModel.State.Result).list) {
                        cartLineList.add(carline.toData())
                    }

                    BooksLazyColumn(cartLineList = cartLineList) { screenModel.update(it) }
                }

            }
        )


    }


    @Composable
    fun BooksLazyColumn(
        cartLineList: List<ShoppingCartLineData>,
        updateCartLine: (book: ShoppingCartLineData) -> Unit
    ) {
        LazyColumn {

            for (cartLine in cartLineList) {
                item {

                    BookCard(
                        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        cartLine = cartLine,
                        updateLine = {
                            cartLine.quantity += 1
                            updateCartLine(cartLine)
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
fun BookCard(
    textColor: Color,
    backgroundColor: Color,
    cartLine: ShoppingCartLineData,
    updateLine: (cartLine: ShoppingCartLineData) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val book = bookList.first { it.id == cartLine.bookId }
    var quantity by remember { mutableStateOf(cartLine.quantity) }
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

                    Text(quantity.toString())

                    // add shopping icon
                    ExtendedFloatingActionButton(onClick = {
                        updateLine(cartLine)
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
}