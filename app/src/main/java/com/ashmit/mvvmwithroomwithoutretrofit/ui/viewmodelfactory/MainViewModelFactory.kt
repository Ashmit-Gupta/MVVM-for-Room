package com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel.MainViewModel

class MainViewModelFactory(private val repository: Repository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}