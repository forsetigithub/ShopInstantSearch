package com.example.shopinstantsearch.searchmain

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.data.ShopDatabaseDao
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ShopListViewModel(
    application: Application
) : ViewModel() {

    private val shopRepository = ShopRepository(ShopDatabase.getInstance(application))

    var shops =  shopRepository.shops


    init {
        getShops()
    }

    suspend fun getQueryShops(query: String) : LiveData<List<ShopInfo>> {
        return withContext(Dispatchers.IO) {
            shopRepository.performSearch(query)
        }
    }

    private fun getShops() {
        viewModelScope.launch {
            try {
                shopRepository.refreshShops()
            }catch (e: Exception) {
                Log.e("ShopListViewModel",e.stackTraceToString())
            }
        }
    }
}