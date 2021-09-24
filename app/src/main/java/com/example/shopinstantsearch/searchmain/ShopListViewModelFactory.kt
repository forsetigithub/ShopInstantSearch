package com.example.shopinstantsearch.searchmain


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.repository.ShopRepository

class ShopListViewModelFactory(
    private val shopRepository: ShopRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(ShopListViewModel::class.java)) {
          return ShopListViewModel(shopRepository) as T
      }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}