package com.lduboscq.appkickstarter.main.book

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class ShoppingCartLine : RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()
    var bookId: String = RealmUUID.random().toString()
    var quantity: Int = 1

    fun toData(): ShoppingCartLineData {
        return ShoppingCartLineData(
            id = this._id,
            bookId = this.bookId,
            quantity = this.quantity,
            shoppingCartLine = this
        )
    }
}


data class ShoppingCartLineData(
    var id: String? = null,
    var bookId: String? = null,
    var quantity: Int = 1,
    var shoppingCartLine: ShoppingCartLine?
)