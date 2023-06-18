package com.lduboscq.appkickstarter.main.data

import com.lduboscq.appkickstarter.Frog
import com.lduboscq.appkickstarter.FrogData

interface FrogRepositoryInterface {

    suspend fun getAllFrog(): List<Frog>
    suspend fun getFrog(fragName: String): List<Frog>
    suspend fun addFrog(name: String, age: Int): List<Frog>
    suspend fun updateFrog(frog: FrogData): List<Frog>
    suspend fun deleteFrog(id: String): List<Frog>
}