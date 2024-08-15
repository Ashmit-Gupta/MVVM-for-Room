package com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
):ViewModel() {

    fun getQuotes():LiveData<List<Quote>> = repository.getQuotes()

    fun insertQuote(quote:Quote) {
        viewModelScope.launch{(Dispatchers.IO)
            repository.insertQuote(quote)
        }
    }

    fun updateQuote(quote:Quote){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateQuote(quote)
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteQuote(quote)
        }
    }

    //update and getUserBy id mai se Live Data remove kaar de !!
    fun getUserById(id :Int) :LiveData<Quote>{
        return repository.getQuoteById(id)
    }
}