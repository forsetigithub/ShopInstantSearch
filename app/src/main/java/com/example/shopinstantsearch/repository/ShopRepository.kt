package com.example.shopinstantsearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabaseDao
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SearchApi {
    fun performSearch(query: String) : Flow<List<ShopInfo>>
}

class ShopRepository @Inject constructor (private val shopDao: ShopDatabaseDao) : SearchApi {

    val shops: LiveData<List<ShopInfo>> =
        Transformations.map(shopDao.getAllShops().asLiveData()) {
            it.asDomainModel()
        }

    suspend fun refreshShops() {
        withContext(Dispatchers.IO) {
            val shops = ShopApi.retrofitService.getShops()
            shopDao.insertAll(shops)
        }
    }

    override fun performSearch(query: String) : Flow<List<ShopInfo>> {
        return shopDao.search("%$query%")
    }
}