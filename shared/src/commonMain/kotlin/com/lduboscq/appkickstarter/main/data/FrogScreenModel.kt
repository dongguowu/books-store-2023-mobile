package com.lduboscq.appkickstarter

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.lduboscq.appkickstarter.main.data.Frog
import com.lduboscq.appkickstarter.main.data.FrogData
import com.lduboscq.appkickstarter.main.data.FrogRepositoryInterface
import kotlinx.coroutines.launch

class FrogScreenModel(private val repository: FrogRepositoryInterface) :
    StateScreenModel<FrogScreenModel.State>(State.Init) {

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Result(val frogList: List<Frog>) : State()
        //TODO: singleResult and multipleResult
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
            mutableState.value = State.Result(
                frogList = repository.addFrog(
                    name = name,
                    age = 1,
                )
            )
        }
    }

    fun deleteFrog(id: String) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(frogList = repository.deleteFrog(id))
        }
    }

    fun updateFrog(frog: FrogData) {
        coroutineScope.launch {
            mutableState.value = State.Loading
            mutableState.value =
                State.Result(frogList = repository.updateFrog(frog))
        }
    }
}
