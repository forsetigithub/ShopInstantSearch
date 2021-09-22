package com.example.shopinstantsearch.searchmain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shopinstantsearch.R
import com.example.shopinstantsearch.data.DatabaseModule
import com.example.shopinstantsearch.databinding.FragmentShopSearchMainBinding
import com.example.shopinstantsearch.getQueryTextChangeStateFlow
import com.example.shopinstantsearch.repository.ShopRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext


class ShopListFragment : Fragment(),CoroutineScope {

    private lateinit var job: Job
    private lateinit var binding: FragmentShopSearchMainBinding
    private val adapter:ShopListAdapter by lazy { ShopListAdapter() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shop_search_main,container,false)

        binding.loadingSpinner.visibility = ProgressBar.VISIBLE

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            ShopListViewModelFactory(ShopRepository(DatabaseModule.provideDao(DatabaseModule.provideDatabase(application))))

        val shopListViewModel =
            ViewModelProvider(this,viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopListViewModel = shopListViewModel
        binding.lifecycleOwner = this

        binding.shopList.adapter = adapter

        job = Job()

//        binding.shopListViewModel?.getQueryShops(binding.addressSearchView.query.toString())?.observe(this@ShopListFragment.viewLifecycleOwner,{ list ->
//            list?.let {
//
//                Log.i("setupSearchStateFlow",binding.addressSearchView.query.toString())
//                adapter.submitList(it)
//                binding.loadingSpinner.visibility = ProgressBar.INVISIBLE
//            }
//        })

        setupSearchStateFlow()

        binding.loadingSpinner.visibility = ProgressBar.INVISIBLE

        return binding.root
    }

    @OptIn(kotlinx.coroutines.FlowPreview::class)
    private fun setupSearchStateFlow() {
        launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            binding.addressSearchView.getQueryTextChangeStateFlow()
                .debounce(700)
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

                    binding.shopListViewModel?.getQueryShops(result)?.observe(this@ShopListFragment.viewLifecycleOwner,{ list ->
                        list?.let {
                            Log.i("setupSearchStateFlow",it.size.toString())
                            adapter.submitList(it)
                            binding.loadingSpinner.visibility = ProgressBar.INVISIBLE
                        }
                    })
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