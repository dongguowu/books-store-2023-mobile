package com.lduboscq.appkickstarter.main.ui.infoscreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.lduboscq.appkickstarter.main.book.BookData


internal class DetailScreen(var book: BookData) : Screen {

    @Composable
    override fun Content() {
        Text(book.title)
    }
}