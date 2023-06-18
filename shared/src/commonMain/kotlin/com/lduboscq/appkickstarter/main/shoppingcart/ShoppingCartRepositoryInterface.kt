package com.lduboscq.appkickstarter.main.shoppingcart

interface ShoppingCartRepositoryInterface {

    suspend fun getAll(): List<CartLine>
    suspend fun getByBookId(bookId: String): List<CartLine>
    suspend fun addOrUpdate(cartLineData: CartLineData): List<CartLine>
    suspend fun delete(id: String): List<CartLine>
}