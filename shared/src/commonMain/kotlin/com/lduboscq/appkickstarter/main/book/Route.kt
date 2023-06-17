package com.lduboscq.appkickstarter.main.book

import com.lduboscq.appkickstarter.model.BookData
import com.lduboscq.appkickstarter.model.User


sealed class Route {
    data class Home(val user: User?) : Route()
    data class About(val count: Int) : Route()
    data class Detail(val book: BookData) : Route()
    data class ShoppingCart(val list: List<ShoppingCartLineData>) : Route()
}