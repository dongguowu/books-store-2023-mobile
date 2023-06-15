package com.lduboscq.appkickstarter.main.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class BookSchema : RealmObject {
    @PrimaryKey
    var _id: String = ""

    @Index
    var title: String = "a string for title."
    var price: Int = 100
    var imageUrl: String = "a url link to image"
}