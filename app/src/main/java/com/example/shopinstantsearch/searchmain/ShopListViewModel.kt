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

    //TODO DBからではなく、shopsから抽出するように変更
    val shopList: LiveData<List<ShopInfo>> = Transformations.switchMap(shopFilterQuery) {
        param ->
            shopRepository.performSearch(param)
    }

    //TODO 初回データ取り込み時にProgressBarが表示されない問題あり
    val shops: LiveData<List<ShopInfo>> by lazy {
        shopRepository.getAllShop()
    }

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

            }catch (e: Exception) {
                Log.e("ShopListViewModel",e.stackTraceToString())
            }
        }
    }
}