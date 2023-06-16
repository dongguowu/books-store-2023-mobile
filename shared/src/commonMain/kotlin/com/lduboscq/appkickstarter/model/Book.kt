package com.lduboscq.appkickstarter.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey


//class Book: RealmObject {
//    @PrimaryKey
//    var _id: String = RealmUUID.random().toString()
//
//    @Index
//    var title: String = ""
//    var imagePath: String = ""
//    var description: String = ""
//    var categories: String = ""
//    var price: Int = 999
//}

/** Since we can't access the content of a Frog realm object without
 *    a lot of complications, this stores the latest information
 *    explicitly for the view.  We also store the Frog that supplied
 *    the information in case we need it.
 *  We'll use id instead of _id since it is more general
 */
data class Book(val id: String, val title: String, val imagePath: String)
