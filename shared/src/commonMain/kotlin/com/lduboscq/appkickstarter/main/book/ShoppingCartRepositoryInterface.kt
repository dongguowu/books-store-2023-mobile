package com.lduboscq.appkickstarter.main.book

import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepositoryInterface {
    suspend fun getAll(): List<ShoppingCartLine>
    suspend fun add(line: ShoppingCartLineData): List<ShoppingCartLine>
    suspend fun delete(line: ShoppingCartLineData): List<ShoppingCartLine>
    suspend fun update(line: ShoppingCartLineData): List<ShoppingCartLine>
    suspend fun getAllFlow(): Flow<List<ShoppingCartLine>>
    fun convertToData(cartLine: ShoppingCartLine): ShoppingCartLineData {
        return ShoppingCartLineData(
            id = cartLine._id,
            bookId = cartLine.bookId,
            quantity = cartLine.quantity,
            shoppingCartLine = cartLine
        )
    }


}