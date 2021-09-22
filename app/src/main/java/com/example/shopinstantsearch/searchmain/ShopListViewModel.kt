package com.example.shopinstantsearch.searchmain


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShopListViewModel @ViewModelInject constructor (
    private val shopRepository: ShopRepository
) : ViewModel() {

    //TODO デバイス回転時の対応
//    val shop_list:LiveData<List<ShopInfo>> = getQueryShops(query)

    init {
        getShops()
    }

    fun getQueryShops(query: String) : LiveData<List<ShopInfo>>{
        return shopRepository.performSearch(query).asLiveData()
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