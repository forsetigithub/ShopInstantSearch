package com.example.shopinstantsearch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinstantsearch.data.ShopInfo
import kotlinx.coroutines.flow.asFlow

class ShopListViewModel(
    application: Application
) : ViewModel() {

    val addresslist: MutableLiveData<MutableList<ShopInfo>> by lazy {
        MutableLiveData<MutableList<ShopInfo>>()
    }

    init {
        addresslist.postValue(
            mutableListOf<ShopInfo>(
            ShopInfo(shopId = 1,shopCode = "01",address1 = "福岡県福岡市",address2 = "南区向野"),
            ShopInfo(shopId = 2,shopCode = "02",address1 = "福岡県久留米市",address2 = "東町")
        ))
    }

}