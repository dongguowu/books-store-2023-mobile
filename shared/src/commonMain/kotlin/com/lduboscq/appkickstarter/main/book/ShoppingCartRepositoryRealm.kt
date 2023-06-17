package com.lduboscq.appkickstarter.main.book

import io.realm.kotlin.Realm
import io.realm.kotlin.mongodb.sync.ConnectionState
import io.realm.kotlin.mongodb.syncSession
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

abstract class ShoppingCartRepositoryRealm : ShoppingCartRepositoryInterface {

    lateinit var realm: Realm
    private var currentJob: Job? = null


    private fun cancelCurrentJob() {
        currentJob?.cancel()
        currentJob = null
    }

    abstract suspend fun setupRealmSync()

    private fun closeRealmSync() {
        realm.close()
    }

    override suspend fun getAll(): List<ShoppingCartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        return realm.query(ShoppingCartLine::class).find()
    }



    override suspend fun add(line: ShoppingCartLineData): List<ShoppingCartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        val syncSession = realm.syncSession
        if (syncSession.connectionState != ConnectionState.CONNECTED) {
            // Handle the case when the sync subscription is not complete yet
            // You might want to delay the write operation or handle it accordingly
            // For example, you can use a callback or coroutine suspension until the subscription is complete
            println("######################\n not connectedA")
            return getAll()
        }

        var lineDb: ShoppingCartLine? = null

        line.bookId?.let { inputBookId ->
            realm.write {
                lineDb = this.copyToRealm(ShoppingCartLine().apply {
                    _id = line?.id ?: RealmUUID.random().toString()
                    bookId = inputBookId
                    quantity = line.quantity
                })
            }
        }

//        line.bookId?.let { bookId ->
//            realm.writeBlocking {
//                val lineDb: ShoppingCartLine = this.copyToRealm(ShoppingCartLine())
//                lineDb.bookId = bookId
//                lineDb.quantity = line.quantity
//            }
//        }


        return getAll()
    }

    override suspend fun delete(line: ShoppingCartLineData): List<ShoppingCartLine> {
        TODO("Not yet implemented")
        return getAll()
    }

    override suspend fun update(line: ShoppingCartLineData): List<ShoppingCartLine> {
        TODO("Not yet implemented")
        return getAll()
    }

    override suspend fun getAllFlow(): Flow<List<ShoppingCartLine>> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        var frogsState: Flow<List<ShoppingCartLine>> = flowOf(emptyList())
        cancelCurrentJob()

        currentJob = CoroutineScope(Dispatchers.Default).launch {
            // Listen to the Realm query result as a Flow
            val frogFlow: Flow<ResultsChange<ShoppingCartLine>> =
                realm.query<ShoppingCartLine>(ShoppingCartLine::class).find().asFlow()

            frogFlow.collect { resultsChange: ResultsChange<ShoppingCartLine> ->
                // Convert each Frog object to FrogData
                val frogs = resultsChange.list

                frogsState = flowOf(frogs)
            }
        }

        return frogsState
    }
}