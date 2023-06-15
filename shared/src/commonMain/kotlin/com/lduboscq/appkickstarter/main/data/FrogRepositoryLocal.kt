package com.lduboscq.appkickstarter

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.internal.platform.runBlocking
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.schema.RealmStorageType
import io.realm.kotlin.types.MutableRealmInt
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FrogRepositoryLocal() {
    lateinit var realm: Realm
    private fun setupRealmSync() {
        val config = RealmConfiguration.Builder(setOf(Frog::class))
            .inMemory()
            .build()
        realm = Realm.open(config)
    }

    private fun closeRealmSync() {
        realm.close()
    }

    suspend fun addFrog(name1: String, age1: Int, species1: String, owner1: String): Frog? {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        var frog2: Frog? = null
        realm.writeBlocking {
            // create a frog object in the realm
//            val froggy = Frog().apply {
//                _id = RealmUUID.random().toString()
//                name = name1
//                age = MutableRealmInt.create(age1.toLong())
//                species = species1
//                owner = owner1
//            }
//            frog2 = copyToRealm(froggy)
            val frog3: Frog = this.copyToRealm(Frog())
            frog3.name = name1
            frog3.age = MutableRealmInt.create(age1.toLong())
            frog3.species = species1
            frog3.owner = owner1
            frog2 = frog3
        }

        return frog2
    }

    fun getFrog(): Frog? {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        // Search equality on the primary key field name
        val frog: Frog? = realm.query<Frog>(Frog::class, "name = 'Kermit'").first().find()

        return frog
    }
}
