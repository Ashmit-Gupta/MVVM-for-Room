package com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.QuoteModel
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) :ViewModel(){


    fun getQuotes():LiveData<List<QuoteModel>>{
             return repository.getQuotes()
    }

    fun insertQuote(quote:QuoteModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertQuote(quote)
        }
    }
}