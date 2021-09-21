package com.example.shopinstantsearch.searchmain

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.data.ShopDatabaseDao

class ShopListViewModelFactory(
    private val application: Application

) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(ShopListViewModel::class.java)) {
          return ShopListViewModel(application) as T
      }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}