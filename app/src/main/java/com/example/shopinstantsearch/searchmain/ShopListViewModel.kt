package com.example.shopinstantsearch.searchmain

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shopinstantsearch.api.ShopApi
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.data.ShopDatabaseDao
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ShopListViewModel(
    application: Application
) : ViewModel() {

    private val shopRepository = ShopRepository(ShopDatabase.getInstance(application))

    val shops = shopRepository.shops

    init {
        getShops()
    }

    private fun getShops() {
        viewModelScope.launch {
            try {
                shopRepository.refreshShops()
            }catch (e: Exception) {
                Log.e("ShopListViewModel",e.message.toString())
            }
        }
    }


}