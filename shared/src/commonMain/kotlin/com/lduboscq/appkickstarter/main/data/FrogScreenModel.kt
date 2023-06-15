package com.lduboscq.appkickstarter

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class FrogScreenModel(private val repository: FrogRepositoryRemote)
    : StateScreenModel<FrogScreenModel.State>(State.Init) {

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val frogList: List<Frog>) : State()
    }

    fun getFrog(name: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(frogList = repository.getFrog(name))
        }
    }

    fun addFrog(name: String) {
         coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(frogList = repository.addFrog(
                name = name,
                age = 45,
                species = "Green",
                owner = "Jim"))
        }
    }

    fun deleteFrog(id: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(frogList = repository.deleteFrog(id))
        }
    }

}
