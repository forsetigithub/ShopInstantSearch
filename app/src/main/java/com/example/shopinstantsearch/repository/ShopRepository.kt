package com.example.shopinstantsearch.repository

import androidx.lifecycle.LiveData
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabaseDao
import com.example.shopinstantsearch.data.ShopInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SearchApi {
    fun performSearch(query: String) : LiveData<List<ShopInfo>>
}

class ShopRepository @Inject constructor (private val shopDao: ShopDatabaseDao) : SearchApi {

    suspend fun refreshShops() {
        withContext(Dispatchers.IO) {
            val shops = ShopApi.retrofitService.getShops()
            shopDao.insertAll(shops)
        }
    }

    override fun performSearch(query: String) : LiveData<List<ShopInfo>> {
        return shopDao.search("%$query%")
    }
}