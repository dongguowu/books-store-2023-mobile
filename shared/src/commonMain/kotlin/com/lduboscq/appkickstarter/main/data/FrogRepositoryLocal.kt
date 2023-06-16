package com.lduboscq.appkickstarter

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.MutableRealmInt

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

    suspend fun addFrog(name: String, age: Int, species: String, owner: String): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val frog: Frog = this.copyToRealm(Frog())
            frog.name = name
            frog.age = age
            frog.species = species
            frog.owner = owner
        }

        return realm.query<Frog>(Frog::class).find()
    }

    suspend fun getFrog(name: String): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        // Search equality on the primary key field name
        return realm.query(Frog::class, "name == $0", name).find()
    }

    suspend fun deleteFrog(id: String): Unit {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        val frogDb = realm.query(Frog::class, "_id == $0", id).first().find()
        if (frogDb == null) {
            println("****************************************\n" + "not found frog(id= ${id}}")
            return
        }
        // Search equality on the primary key field name
        return
    }
}
