package com.lduboscq.appkickstarter.main.data

import com.lduboscq.appkickstarter.Frog

interface FrogRepositoryInterface {
    suspend fun getFrog(fragName: String): List<Frog>
    suspend fun addFrog(name: String, age: Int, species: String, owner: String): List<Frog>
    suspend fun deleteFrog(id: String): List<Frog>
}