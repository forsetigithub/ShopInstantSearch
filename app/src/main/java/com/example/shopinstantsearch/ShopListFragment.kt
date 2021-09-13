package com.example.shopinstantsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.databinding.FragmentShopSearchMainBinding
import java.util.logging.Logger


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

        val viewModelFactory = ShopListViewModelFactory(application)

        val shopListViewModel =
            ViewModelProvider(this,viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopListViewModel = shopListViewModel
        binding.lifecycleOwner = this


        val adapter = ShopInfoAdapter()

        binding.shopList.adapter = adapter

        shopListViewModel.addresslist.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}