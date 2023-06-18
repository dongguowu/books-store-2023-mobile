package com.lduboscq.appkickstarter.main.cart

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId

class Cart(
    var bookId: String? = null,
    var quantity: Int? = null,
) : RealmObject {
    @PrimaryKey
    var _id: BsonObjectId = BsonObjectId()
}

