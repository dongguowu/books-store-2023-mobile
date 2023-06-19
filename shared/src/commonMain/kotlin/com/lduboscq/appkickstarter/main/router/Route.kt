package com.lduboscq.appkickstarter.main.router

import com.lduboscq.appkickstarter.main.model.BookData
import com.lduboscq.appkickstarter.main.model.User


sealed class Route {
    data class Home(val user: User?) : Route()
    data class About(val feature: String) : Route()
    data class Detail(val book: BookData?) : Route()
    data class ShoppingCart(val quantity: Int) : Route()
}