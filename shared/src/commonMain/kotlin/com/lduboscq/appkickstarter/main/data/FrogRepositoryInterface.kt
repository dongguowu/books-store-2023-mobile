package com.lduboscq.appkickstarter.main.data

interface FrogRepositoryInterface {

    suspend fun getAll(): List<CartLine>
    suspend fun getByBookId(bookId: String): List<CartLine>
    suspend fun addOrUpdate(bookId: String, quantity: Int): List<CartLine>
    suspend fun update(cartLineData: CartLineData): List<CartLine>
    suspend fun delete(id: String): List<CartLine>
}