package com.lduboscq.appkickstarter.main.book

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartScreenModel(private val repository: ShoppingCartRepositoryInterface) : StateScreenModel<ShoppingCartScreenModel.State>(State.Init) {

    private val _list = MutableStateFlow<List<ShoppingCartLine>>(emptyList())
    val shoppingCartDataState: StateFlow<List<ShoppingCartLine>> = _list.asStateFlow()

    init {
        coroutineScope.launch {
            var shoppingCartState: Flow<List<ShoppingCartLine>> = repository.getAllFlow()
        }
    }

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val list: List<ShoppingCartLine>) : State()
    }

    fun getAll() {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(list = repository.getAll())
        }
    }

    fun add(line: ShoppingCartLineData) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(
                list = repository.add(line)
            )
        }
    }

//    fun deleteFrog(id: String) {
//        coroutineScope.launch {
//            mutableState.value = State.Loading
//            mutableState.value = State.Result(list = repository.deleteFrog(id))
//        }
//    }
//
//    fun updateFrog(id: String, name: String) {
//        coroutineScope.launch {
//            mutableState.value = State.Loading
//            mutableState.value =
//                State.Result(list = repository.updateFrog(id = id, name = name))
//        }
//    }
}
