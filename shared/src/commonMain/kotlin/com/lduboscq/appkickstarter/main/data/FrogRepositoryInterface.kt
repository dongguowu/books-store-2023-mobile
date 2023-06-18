package com.lduboscq.appkickstarter.main.data

import com.lduboscq.appkickstarter.Frog

interface FrogRepositoryInterface {

    suspend fun getAllFrog(): List<Frog>
    suspend fun getFrog(fragName: String): List<Frog>
    suspend fun addFrog(name: String, age: Int): List<Frog>
    suspend fun updateFrog(id: String, age: Int): List<Frog>
    suspend fun deleteFrog(id: String): List<Frog>
}