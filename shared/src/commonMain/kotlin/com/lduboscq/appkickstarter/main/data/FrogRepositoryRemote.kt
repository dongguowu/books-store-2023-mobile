package com.lduboscq.appkickstarter

import com.lduboscq.appkickstarter.main.data.CartLine
import com.lduboscq.appkickstarter.main.data.CartLineData
import com.lduboscq.appkickstarter.main.data.FrogRepositoryInterface
import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.SyncSession

class FrogRepositoryRemote() : FrogRepositoryInterface {

    lateinit var realm: Realm

    private val appServiceInstance by lazy {
        val configuration =
            AppConfiguration.Builder("application-shopping-cart-lines-xyaxh").log(LogLevel.ALL)
                .build()
        App.create(configuration)
    }

    private suspend fun setupRealmSync() {
        val user =
            appServiceInstance.login(Credentials.apiKey("6xSR1H5UG8jTPgebJd6oedds7xzdiaqKLg97QJomuNsJ6g9unNat4bwf2MjEhM7g"))

        println("Got Here")
        val config = SyncConfiguration.Builder(user, setOf(CartLine::class))
            .initialSubscriptions(rerunOnOpen = true) { realm ->
                add(
                    realm.query<CartLine>(
                        CartLine::class,
                        "_id == $0",
                        user.id
                    ),
                    name = "shoppingcart",
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

    override suspend fun addOrUpdate(
        cartLineData: CartLineData
    ): List<CartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val cartLineDb =
                query(CartLine::class, "bookId == $0", cartLineData.bookId).first().find()
            if (cartLineDb == null) {
                // Add new
                val cartLine: CartLine = this.copyToRealm(CartLine())
                cartLine.bookId = cartLineData.bookId
                cartLine.quantity = cartLineData.quantity
            } else {
                // Update
                cartLineDb.quantity = cartLineData.quantity
            }
        }

        return getAll()
    }

    override suspend fun update(frogToUpdate: CartLineData): List<CartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val cartLineDb = query(CartLine::class, "_id == $0", frogToUpdate.id).first().find()
            if (cartLineDb == null) {
                println("****************************************\n" + "not found frog(id= ${frogToUpdate.id}}")
            }
            cartLineDb?.quantity = frogToUpdate.quantity
        }

        return getAll()
    }

    override suspend fun getByBookId(bookId: String): List<CartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        // return all frogs on database
        if (bookId.isEmpty()) {
            return getAll()
        }
        // Search equality on the primary key field name
        return realm.query(CartLine::class, "bookId == $0", bookId).find()
    }

    override suspend fun delete(id: String): List<CartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        realm.writeBlocking {
            val cartLineDb = query(CartLine::class, "_id == $0", id).first().find()
            if (cartLineDb == null) {
                println("****************************************\n" + "not found frog(id= ${id}}")
            }
            cartLineDb?.let { delete(it) }
        }
        return getAll()
    }

    override suspend fun getAll(): List<CartLine> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        var list = realm.query(CartLine::class).find()
//        closeRealmSync()
        return list
    }
}
