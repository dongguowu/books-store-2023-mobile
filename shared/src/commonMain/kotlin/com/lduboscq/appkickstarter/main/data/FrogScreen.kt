package com.lduboscq.appkickstarter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.lduboscq.appkickstarter.main.book.MyBottomBar
import com.lduboscq.appkickstarter.main.book.MyTopBar
import com.lduboscq.appkickstarter.main.book.Route
import com.lduboscq.appkickstarter.main.book.ShoppingCartLineData
import com.lduboscq.appkickstarter.main.book.getBookList

class FrogScreen : Screen {


    private val bookList = getBookList()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel() { FrogScreenModel(FrogRepositoryRemote()) }
        val state by screenModel.state.collectAsState()

        screenModel.getFrog("")


        val cartLineList = mutableListOf<ShoppingCartLineData>()
        val list = mutableListOf<Frog>()

        if (state is FrogScreenModel.State.Result) {
            for (frog in (state as FrogScreenModel.State.Result).frogList) {
                cartLineList.add(frog.toData())
            }
        }

        var message by remember { mutableStateOf("") }
        when (val result = state) {
            is FrogScreenModel.State.Init -> message = "Just initialized"
            is FrogScreenModel.State.Loading -> message = "Loading"
            is FrogScreenModel.State.Result -> message = "Success"
            else -> {}
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = { MyTopBar(message, false, scrollBehavior) },

            bottomBar = {
                MyBottomBar(
                    list = cartLineList,
                    totalQuantity = list.size,
                    currentScreen = Route.ShoppingCart(cartLineList)
                )
            },

            content = {
                if (state is FrogScreenModel.State.Result) {
                    val list = (state as FrogScreenModel.State.Result).frogList
                    CartLazyColumn(
                        cartLineList = list,
                        update = { frog -> screenModel.updateFrog(frog) },
                        delete = { id -> screenModel.deleteFrog(id) }

                    )
                }
            }
        )
    }

    @Composable
    fun CartLazyColumn(
        cartLineList: List<Frog>,
        update: (frog: FrogData) -> Unit,
        delete: (id: String) -> Unit
    ) {
        for (frog in cartLineList) {

            Card() {
                Column {
                    Text("name: ${frog.name}")
                    Text("age: ${frog?.age?.toString()}")
                }
                Button(
                    content = { Text("+") },
                    onClick = {
                        update(FrogData(id = frog._id, age = frog.age + 1))
                    },
                )
                Spacer(modifier = Modifier.height(9.dp))
                Button(
                    content = { Text("-") },
                    onClick = {
                        var ageToUpdate = frog.age - 1
                        if (ageToUpdate <= 0) {
                            delete(frog._id)
                        } else {
                            update(FrogData(id = frog._id, age = ageToUpdate))
                        }
                    },
                )
            }
        }
    }


}