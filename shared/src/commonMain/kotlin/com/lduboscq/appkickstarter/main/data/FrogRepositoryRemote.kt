package com.lduboscq.appkickstarter

import com.lduboscq.appkickstarter.main.data.FrogRepositoryInterface
import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.SyncSession
import io.realm.kotlin.types.MutableRealmInt

class FrogRepositoryRemote() : FrogRepositoryInterface {

    lateinit var realm: Realm

    private val appServiceInstance by lazy {
        val configuration =
            AppConfiguration.Builder("application-0-luysu").log(LogLevel.ALL)
                .build()
        App.create(configuration)
    }

    private suspend fun setupRealmSync() {
        val user =
            appServiceInstance.login(Credentials.apiKey("D57YZuuBYkKYTxopA4isfgJbGlGVLmiKhluhqHOev8GyAMrizUr9XINvHKGxEvVH"))

        println("Got Here")
        val config = SyncConfiguration.Builder(user, setOf(Frog::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<Frog>(
                        Frog::class,
                        "_id == $0",
                        user.id
                    ),
                    name = "FrogSub",
                    updateExisting = true
                )
            }
            .errorHandler { session: SyncSession, error: SyncException ->
                println("****************************************\n" + error)
            }
            .build()
        realm = Realm.open(config)
    }

    private fun closeRealmSync() {
        realm.close()
    }

    override suspend fun addFrog(
        name: String,
        age: Int,
        species: String,
        owner: String
    ): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val frog: Frog = this.copyToRealm(Frog())
            frog.name = name
            frog.age = MutableRealmInt.create(age.toLong())
            frog.species = species
            frog.owner = owner
        }

        return getAllFrog()
    }

    override suspend fun getFrog(name: String): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        // return all frogs on database
        if (name.isEmpty()) {
            return getAllFrog()
        }
        // Search equality on the primary key field name
        return realm.query(Frog::class, "name == $0", name).find()
    }

    override suspend fun deleteFrog(id: String): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val frogDb = query(Frog::class, "_id == $0", id).first().find()
            if (frogDb == null) {
                println("****************************************\n" + "not found frog(id= ${id}}")
            }
            frogDb?.let { delete(it) }
        }
        return getAllFrog()
    }

    private suspend fun getAllFrog(): List<Frog> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        var list = realm.query(Frog::class).find()
//        closeRealmSync()
        return list
    }
}
