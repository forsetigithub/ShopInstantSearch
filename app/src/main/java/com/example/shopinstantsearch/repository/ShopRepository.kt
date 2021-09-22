package com.example.shopinstantsearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SearchApi {
    suspend fun performSearch(query: String) : LiveData<List<ShopInfo>>
}

class ShopRepository(private val database: ShopDatabase) : SearchApi {

    val shops: LiveData<List<ShopInfo>> =
        Transformations.map(database.shopDatabaseDao.getAllShops()) {
            it.asDomainModel()
        }

    suspend fun refreshShops() {
        withContext(Dispatchers.IO) {
            val shops = ShopApi.retrofitService.getShops()
            database.shopDatabaseDao.insertAll(shops)
        }
    }

    override suspend fun performSearch(query: String) : LiveData<List<ShopInfo>> {

        val result = database.shopDatabaseDao.search(query)

        Log.i("performSearch",result.value?.size.toString())
        return result
    }


}