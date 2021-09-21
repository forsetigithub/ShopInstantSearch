package com.example.shopinstantsearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopRepository(private val database: ShopDatabase) {

    val shops: LiveData<List<ShopInfo>> = Transformations.map(database.shopDatabaseDao.getAllShops()) {
        it.asDomainModel()
    }
    suspend fun refreshShops() {
        withContext(Dispatchers.IO) {
            val shops = ShopApi.retrofitService.getShops()
            database.shopDatabaseDao.insertAll(shops)
        }
    }
}