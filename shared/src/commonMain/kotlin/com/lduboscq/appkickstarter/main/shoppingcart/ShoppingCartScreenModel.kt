package com.lduboscq.appkickstarter

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.lduboscq.appkickstarter.main.shoppingcart.CartLine
import com.lduboscq.appkickstarter.main.shoppingcart.CartLineData
import com.lduboscq.appkickstarter.main.shoppingcart.ShoppingCartRepositoryInterface
import kotlinx.coroutines.launch

class ShoppingCartScreenModel(private val repository: ShoppingCartRepositoryInterface) :
    StateScreenModel<ShoppingCartScreenModel.State>(State.Init) {

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val cartLineList: List<CartLine>) : State()
        //TODO: singleResult and multipleResult
    }

    fun getCartLineByBookId(bookId: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(cartLineList = repository.getByBookId(bookId))
        }
    }


    fun addOrUpdateCartLine(cartLine: CartLineData) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(
                cartLineList = repository.addOrUpdate(cartLine)
            )
        }
    }

    fun deleteCartLineByBookId(bookId: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(cartLineList = repository.delete(bookId))
        }
    }


}
