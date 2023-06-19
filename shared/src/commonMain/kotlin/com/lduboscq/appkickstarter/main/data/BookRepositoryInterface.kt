package com.lduboscq.appkickstarter.main.data

import com.lduboscq.appkickstarter.main.model.BookData

interface BookRepositoryInterface {
    fun getAll() : List<BookData>
}