package com.lduboscq.appkickstarter.main.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lduboscq.appkickstarter.FrogRepositoryRemote
import com.lduboscq.appkickstarter.FrogScreenModel
import com.lduboscq.appkickstarter.main.Image
import com.lduboscq.appkickstarter.main.book.MyBottomBar
import com.lduboscq.appkickstarter.main.book.MyTopBar
import com.lduboscq.appkickstarter.main.book.Route
import com.lduboscq.appkickstarter.main.book.getBookList
import com.lduboscq.appkickstarter.main.book.screenRouter

class FrogScreen : Screen {

    private val bookList = getBookList()

    @OptIn(ExperimentalMaterial3Api::class)
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

        var quantity by remember { mutableStateOf(0) }
        if (state is FrogScreenModel.State.Result) {
            quantity =
                (state as FrogScreenModel.State.Result).cartLineList.sumOf { frog -> frog.quantity }
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = { MyTopBar(message, scrollBehavior) },

            bottomBar = {
                MyBottomBar(
                    quantity = quantity,
                    currentScreen = Route.ShoppingCart(quantity)
                )
            },

            content = {
                if (state is FrogScreenModel.State.Result) {
                    LazyColumn {
                        for (frog in (state as FrogScreenModel.State.Result).cartLineList) {
                            item {
                                CartLineCard(
                                    cartLine = frog,
                                    update = { frog -> screenModel.updateFrog(frog) },
                                    delete = { id -> screenModel.deleteFrog(id) }

                                )
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun CartLineCard(
        cartLine: CartLine,
        update: (frog: CartLineData) -> Unit,
        delete: (id: String) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val book = bookList.firstOrNull { cartLine.bookId == it.id } ?: return
        Card() {
            Column {
                Text(" ${book.title}")
                Row {
                    Image(
                        url = book.imagePath,
                        modifier = Modifier.size(width = 120.dp, height = 180.dp).padding(15.dp)
                            .clickable(onClick = {
                                navigator.push(screenRouter(Route.Detail(book)))
                            })
                    )
                    AddOrSubstrateQuantity(
                        cartLine = cartLine,
                        update = { update(it) },
                        delete = { delete(it) })
                }
            }

        }
    }
}