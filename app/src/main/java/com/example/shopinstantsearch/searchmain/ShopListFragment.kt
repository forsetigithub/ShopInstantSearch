package com.example.shopinstantsearch.searchmain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.R
import com.example.shopinstantsearch.data.ShopDatabase
import com.example.shopinstantsearch.databinding.FragmentShopSearchMainBinding
import com.example.shopinstantsearch.getQueryTextChangeStateFlow
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ShopListFragment : Fragment(),CoroutineScope {

    private lateinit var job: Job
    private lateinit var binding: FragmentShopSearchMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shop_search_main,container,false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ShopListViewModelFactory(application)

        val shopListViewModel =
            ViewModelProvider(this,viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopListViewModel = shopListViewModel
        binding.lifecycleOwner = this

        val adapter = ShopListAdapter()

        binding.shopList.adapter = adapter

        shopListViewModel.shops.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.i("onCreateView",it.size.toString())
                adapter.submitList(it)
            }
        })

        job = Job()

        setupSearchStateFlow()

        return binding.root
    }

    private fun setupSearchStateFlow() {
        launch {
            binding.addressSearchView.getQueryTextChangeStateFlow()
                .debounce(1000)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    getDataFromText(query)
                        .catch {
                            emitAll(flowOf(""))
                        }
                }
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    binding.loadingSpinner.visibility = ProgressBar.VISIBLE

                    Log.i("setupSearchStateFlow",result)

                    binding.shopListViewModel?.getQueryShops(result)
                    binding.loadingSpinner.visibility = ProgressBar.INVISIBLE
                }
        }
    }

    private fun getDataFromText(query: String): Flow<String> {
        return flow {
            kotlinx.coroutines.delay(500)
            emit(query)
        }
    }


}