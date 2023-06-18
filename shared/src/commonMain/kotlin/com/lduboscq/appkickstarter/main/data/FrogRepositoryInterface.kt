package com.lduboscq.appkickstarter.main.data

interface FrogRepositoryInterface {

    suspend fun getAllFrog(): List<Frog>
    suspend fun getFrog(fragName: String): List<Frog>
    suspend fun addFrog(name: String, age: Int): List<Frog>
    suspend fun updateFrog(frog: FrogData): List<Frog>
    suspend fun deleteFrog(id: String): List<Frog>
}