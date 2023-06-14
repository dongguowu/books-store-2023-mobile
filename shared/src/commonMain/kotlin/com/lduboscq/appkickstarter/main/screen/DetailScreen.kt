package com.lduboscq.appkickstarter.main.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Book


internal class DetailScreen(var book: Book) : Screen {

    @Composable
    override fun Content() {
        Text(book.title)
    }
}