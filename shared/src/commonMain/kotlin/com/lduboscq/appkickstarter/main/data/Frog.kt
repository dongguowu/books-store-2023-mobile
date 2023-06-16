package com.lduboscq.appkickstarter

import io.realm.kotlin.types.MutableRealmInt
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Frog: RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()
    @Index
    var name: String = ""
    var age: Int = 0
    var species: String = ""
    var owner: String = ""
}
