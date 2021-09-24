package com.example.shopinstantsearch

import android.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(newText: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return false
        }
    })

    return query
}