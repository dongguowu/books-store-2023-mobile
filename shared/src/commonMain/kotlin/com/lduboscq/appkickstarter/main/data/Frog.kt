package com.lduboscq.appkickstarter

import io.realm.kotlin.types.MutableRealmInt
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Frog: RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()
    var name: String = "A"
    var age: MutableRealmInt = MutableRealmInt.create(1)
    var species: String = "B"
    var owner: String = "C"
}
