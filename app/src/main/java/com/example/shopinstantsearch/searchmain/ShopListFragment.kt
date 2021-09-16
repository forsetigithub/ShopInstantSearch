package com.example.shopinstantsearch.searchmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.R
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.databinding.FragmentShopSearchMainBinding


class ShopListFragment : Fragment() {
    val TAG_NAME = "ShopListFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentShopSearchMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shop_search_main,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShopDatabase.getInstance(application).shopDatabaseDao
        val viewModelFactory = ShopListViewModelFactory(dataSource, application)

        val shopListViewModel =
            ViewModelProvider(this,viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopListViewModel = shopListViewModel
        binding.lifecycleOwner = this


        val adapter = ShopListAdapter()

        binding.shopList.adapter = adapter

        shopListViewModel.addresses.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}