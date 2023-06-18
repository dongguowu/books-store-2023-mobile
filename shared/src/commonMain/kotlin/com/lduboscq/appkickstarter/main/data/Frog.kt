package com.lduboscq.appkickstarter.main.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

class Frog : RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()

    @Index
    var name: String = ""
    var age: Int = 1

    fun toData(): FrogData {
        return FrogData(
            id = null,
            name = this.name,
            age = this.age,
        )
    }
}

data class FrogData(
    var id: String? = null,
    var name: String = "",
    var age: Int = 1
)
