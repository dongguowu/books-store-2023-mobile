package com.lduboscq.appkickstarter.main.book

import com.lduboscq.appkickstarter.model.BookData


fun getBookList(): List<BookData> {
    return listOf(
        BookData(
            "1",
            "head first kotlin",
            "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
        ),
        BookData(
            "2",
            "kotlin in action",
            "https://kotlinlang.org/docs/images/kotlin-in-action.png"
        ),
        BookData(
            "3",
            "head first kotlin",
            "https://kotlinlang.org/docs/images/head-first-kotlin.jpeg"
        ),
        BookData("4", "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
        BookData(
            "5",
            "kotlin in action",
            "https://kotlinlang.org/docs/images/kotlin-in-action.png"
        ),
        BookData("6", "joy of kotlin", "https://kotlinlang.org/docs/images/joy-of-kotlin.png"),
    )
}