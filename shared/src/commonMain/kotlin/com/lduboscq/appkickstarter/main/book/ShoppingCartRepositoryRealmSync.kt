package com.lduboscq.appkickstarter.main.book

import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.SyncSession

class ShoppingCartRepositoryRealmSync() : ShoppingCartRepositoryRealm() {


    private val appServiceInstance by lazy {
        val configuration =
            AppConfiguration.Builder("application-bookstore-tqcxq").log(LogLevel.ALL)
                .build()
        App.create(configuration)
    }

    override suspend fun setupRealmSync() {
        val user =
            appServiceInstance.login(Credentials.apiKey("qxnxKRCAUFfipXI8q24Kyo286gNoEHCIyRtYsdkoE34leroUvyOCLokpdMwdBDVS"))

        println("*******************************************************************")
        println("Got Here")
        val config = SyncConfiguration.Builder(user, setOf(ShoppingCartLine::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<ShoppingCartLine>(
                        ShoppingCartLine::class,
                        "_id == $0",
                        user.id
                    ),
                    name = "bookstoresub",
                    updateExisting = true
                )
            }
            .errorHandler { session: SyncSession, error: SyncException ->
                println("****************************************\n" + error)
            }
            .build()
        realm = Realm.open(config)
    }
}