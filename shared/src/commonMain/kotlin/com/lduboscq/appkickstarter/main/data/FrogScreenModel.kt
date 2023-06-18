package com.lduboscq.appkickstarter

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.lduboscq.appkickstarter.main.data.CartLine
import com.lduboscq.appkickstarter.main.data.CartLineData
import com.lduboscq.appkickstarter.main.data.FrogRepositoryInterface
import kotlinx.coroutines.launch

class FrogScreenModel(private val repository: FrogRepositoryInterface) :
    StateScreenModel<FrogScreenModel.State>(State.Init) {

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val cartLineList: List<CartLine>) : State()
        //TODO: singleResult and multipleResult
    }

    fun getFrog(name: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(cartLineList = repository.getByBookId(name))
        }
    }


    fun addOrUpdateFrog(cartLine: CartLineData) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(
                cartLineList = repository.addOrUpdate(cartLine)
            )
        }
    }

    fun deleteFrog(id: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(cartLineList = repository.delete(id))
        }
    }

    fun updateFrog(frog: CartLineData) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value =
                State.Result(cartLineList = repository.update(frog))
        }
    }
}
