package com.example.shopinstantsearch.searchmain

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopinstantsearch.data.ShopDatabaseDao
import com.example.shopinstantsearch.data.ShopInfo
import kotlinx.coroutines.launch

class ShopListViewModel(
    datasource: ShopDatabaseDao,
    application: Application
) : ViewModel() {

    private val database = datasource

    val addresses = database.getAllShops()

    init {

       viewModelScope.launch {
           clear()
           insert(ShopInfo(shopCode = "01",address1 = "福岡県福岡市",address2 = "南区向野"))
           insert(ShopInfo(shopCode = "02",address1 = "福岡県久留米市",address2 = "東町"))
           insert(ShopInfo(shopCode = "03",address1 = "北海道札幌市",address2 = "中央区北二十二条西"))

       }

    }

    private suspend fun insert(shop: ShopInfo) {
        database.insert(shop)
    }

    private suspend fun update(shop: ShopInfo) {
        database.update(shop)
    }

    private suspend fun clear() {
        database.clear()
    }





}