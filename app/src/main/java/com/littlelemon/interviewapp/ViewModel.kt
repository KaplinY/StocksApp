package com.littlelemon.interviewapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.littlelemon.interviewapp.data.remote.RetrofitClient
import com.littlelemon.interviewapp.data.remote.StocksApiService
import com.littlelemon.interviewapp.data.remote.StocksResponce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StocksViewModel: ViewModel() {

    private val _stocksState = MutableStateFlow<List<StocksResponce>>(emptyList())
    val stocksState: StateFlow<List<StocksResponce>> = _stocksState

    private val _filterType = MutableStateFlow<String?>(null)
    val filterType: StateFlow<String?> = _filterType

    init {
        fetchStocks()
    }

    fun setFilterType(type: String?) {
        _filterType.value = type
    }

    private fun fetchStocks()  {
        viewModelScope.launch {
            val apiResponce = RetrofitClient.instance.getData()
            _stocksState.value = apiResponce
        }
    }

    val filteredStocksState: StateFlow<List<StocksResponce>> = combine(_stocksState, _filterType) { stocks, filter ->
        if (filter == null) {
            stocks
        } else {
            stocks.filter { it.instrument_type == filter }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}