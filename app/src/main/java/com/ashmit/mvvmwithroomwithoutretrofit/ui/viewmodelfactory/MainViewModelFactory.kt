package com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel.MainViewModel
import javax.inject.Inject

//we wouldnt require factory model if we use dependency Injection cause we would we using the di for the viewModel and no need to explicitly pass the repo

class MainViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}