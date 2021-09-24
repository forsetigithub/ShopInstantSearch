package com.example.shopinstantsearch.searchmain


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.launch

class ShopListViewModel @ViewModelInject constructor (
    private val shopRepository: ShopRepository
) : ViewModel() {

    val shopFilterQuery: MutableLiveData<String> = MutableLiveData()

    val shopList: LiveData<List<ShopInfo>> = Transformations.switchMap(shopFilterQuery) {
        param ->
            shopRepository.performSearch(param)
    }

    private val _shops = MutableLiveData<List<ShopInfo>>()
    val shops: LiveData<List<ShopInfo>>
        get() = _shops

    fun getShopList(query: String) {
        shopFilterQuery.value = query
    }

    init {
        getShops()
    }

    private fun getShops() {
        viewModelScope.launch {
            try {
                shopRepository.refreshShops()
                _shops.postValue(shopRepository.getAllShop().value)

            }catch (e: Exception) {
                Log.e("ShopListViewModel",e.stackTraceToString())
            }
        }
    }
}