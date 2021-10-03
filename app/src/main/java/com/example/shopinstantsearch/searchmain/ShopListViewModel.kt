package com.example.shopinstantsearch.searchmain


import android.util.Log
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.shopinstantsearch.data.ShopInfo
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShopListViewModel @ViewModelInject constructor (
    private val shopRepository: ShopRepository
) : ViewModel() {

    val shopFilterQuery: MutableLiveData<String> = MutableLiveData()

    //TODO DBからではなく、shopsから抽出するように変更
    val shopList: LiveData<List<ShopInfo>> = Transformations.switchMap(shopFilterQuery) {
        param ->
            shopRepository.performSearch(param)
    }

    private val shops = shopRepository.getAllShop()

    val isEmptyShops = shops.map {
        if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
    }

//    fun getAllShops():LiveData<List<ShopInfo>> {
//        return shops
//    }

    fun getShopList(query: String) {
        shopFilterQuery.value = query
    }

    init {
        getShops()
    }

    private fun getShops() {
        viewModelScope.launch {
            try {
                val deferred1 =  async { shopRepository.refreshShops() }
                deferred1.await()

            }catch (e: Exception) {
                Log.e("ShopListViewModel",e.stackTraceToString())
            }
        }
    }
}