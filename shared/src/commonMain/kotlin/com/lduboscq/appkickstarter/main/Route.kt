package com.lduboscq.appkickstarter.main

import com.lduboscq.appkickstarter.model.User
import model.Book


sealed class Route {
    data class Home(val user: User?) : Route()
    data class About(val count: Int) : Route()
    data class Detail(val book: Book): Route()
    data class ShoppingCart(val count: Int): Route()
}