package com.lduboscq.appkickstarter.main.data

import com.lduboscq.appkickstarter.main.model.CartLine
import com.lduboscq.appkickstarter.main.model.CartLineData

interface ShoppingCartRepositoryInterface {

    suspend fun getAll(): List<CartLine>
    suspend fun getByBookId(bookId: String): List<CartLine>
    suspend fun addOrUpdate(cartLineData: CartLineData): List<CartLine>
    suspend fun delete(id: String): List<CartLine>
}