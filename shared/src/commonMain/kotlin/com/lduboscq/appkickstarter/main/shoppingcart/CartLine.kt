package com.lduboscq.appkickstarter.main.shoppingcart

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

class CartLine : RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()

    @Index
    var bookId: String = ""
    var quantity: Int = 1

    fun toData(): CartLineData {
        return CartLineData(
            id = this._id,
            bookId = this.bookId,
            quantity = this.quantity,
        )
    }
}

data class CartLineData(
    var id: String? = null,
    var bookId: String = "",
    var quantity: Int = 1
)
