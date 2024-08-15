package com.ashmit.mvvmwithroomwithoutretrofit

/*
todo Sure! I'll provide the updated code snippets for each of the suggestions mentioned. Here's how you can refactor and improve your existing code.

1. Repository Error Handling Improvement:

Instead of returning dummy data or empty lists, you can use sealed classes to represent the result states (Success, Error) and handle them in your UI.

kotlin

package com.ashmit.mvvmwithroomwithoutretrofit.data.repository

import androidx.lifecycle.LiveData
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDao
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}

class Repository @Inject constructor(private val quoteDao: QuoteDao) {

    suspend fun insertQuote(quote: Quote): Result<Unit> {
        return try {
            quoteDao.insertQuote(quote)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getQuotes(): Flow<Result<List<Quote>>> = flow {
        val quotes = quoteDao.getQuotes()
        emit(Result.Success(quotes))
    }.catch { e ->
        emit(Result.Error(Exception(e)))
    }

    suspend fun updateQuote(quote: Quote): Result<Unit> {
        return try {
            quoteDao.updateQuote(quote)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deleteQuote(quote: Quote): Result<Unit> {
        return try {
            quoteDao.deleteQuote(quote)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getQuoteById(id: Int): Flow<Result<Quote>> = flow {
        val quote = quoteDao.getQuoteById(id)
        emit(Result.Success(quote))
    }.catch { e ->
        emit(Result.Error(Exception(e)))
    }
}

2. ViewModel Handling Different UI States:

Now, update your ViewModel to handle the new Result type and expose data as LiveData or StateFlow.

kotlin

package com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _quotesState = MutableStateFlow<Result<List<Quote>>>(Result.Success(emptyList()))
    val quotesState: StateFlow<Result<List<Quote>>> = _quotesState

    private val _quoteState = MutableStateFlow<Result<Quote>>(Result.Success(Quote(0, "", "")))
    val quoteState: StateFlow<Result<Quote>> = _quoteState

    fun getQuotes() {
        viewModelScope.launch {
            repository.getQuotes().collect {
                _quotesState.value = it
            }
        }
    }

    fun insertQuote(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertQuote(quote)
            // Handle the result if needed
        }
    }

    fun updateQuote(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateQuote(quote)
            // Handle the result if needed
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.deleteQuote(quote)
            // Handle the result if needed
        }
    }

    fun getQuoteById(id: Int) {
        viewModelScope.launch {
            repository.getQuoteById(id).collect {
                _quoteState.value = it
            }
        }
    }
}

3. View Implementation and Error Handling:

Update your MainActivity to observe the StateFlow from ViewModel and handle different UI states.

kotlin

package com.ashmit.mvvmwithroomwithoutretrofit.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ashmit.mvvmwithroomwithoutretrofit.R
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import com.ashmit.mvvmwithroomwithoutretrofit.databinding.ActivityMainBinding
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel.MainViewModel
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        lifecycleScope.launch {
            mainViewModel.quotesState.collect { result ->
                when (result) {
                    is Result.Success -> binding.quote = result.data.toString()
                    is Result.Error -> Toast.makeText(this@MainActivity, "Error: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnGetQuote.setOnClickListener {
            mainViewModel.getQuotes()
        }

        binding.btnGetQuoteById.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.quoteState.collect { result ->
                    when (result) {
                        is Result.Success -> binding.quote = result.data.toString()
                        is Result.Error -> Toast.makeText(this@MainActivity, "Error: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnAddQuote.setOnClickListener {
            mainViewModel.insertQuote(Quote(0, "Learning Room with MVVM", "Via CHEEZY CODE"))
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteQuote.setOnClickListener {
            mainViewModel.deleteQuote(Quote(109, "Some quote", "Some author"))
        }

        binding.btnUpdateQuote.setOnClickListener {
            mainViewModel.updateQuote(Quote(108, "This is the updated quote!!!", "via Hacker"))
        }
    }
}

4. Clean Up Unnecessary Code:

Remove the commented-out code from QuoteDB and other places where it’s no longer relevant.
Conclusion:

This refactored code introduces better error handling using sealed classes and optimizes state management in your UI by leveraging StateFlow. The project structure adheres to MVVM principles with improved clarity and maintainability. The use of Hilt for dependency injection is consistent and efficient.

*/