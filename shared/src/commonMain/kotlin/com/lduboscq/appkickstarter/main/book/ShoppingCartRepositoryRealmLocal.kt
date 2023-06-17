package com.lduboscq.appkickstarter.main.book

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.SyncSession

class ShoppingCartRepositoryRealmLocal() : ShoppingCartRepositoryRealm() {


    override suspend fun setupRealmSync() {
        val config = RealmConfiguration.Builder(setOf(ShoppingCartLine::class))
            .inMemory()
            .build()
        realm = Realm.open(config)
    }
}