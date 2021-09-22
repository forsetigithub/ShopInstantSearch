package com.example.shopinstantsearch

import android.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String): Boolean {
            return false
        }

        override fun onQueryTextChange(p0: String): Boolean {
            query.value = p0
            return false
        }
    })

    return query
}