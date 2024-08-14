package com.ashmit.mvvmwithroomwithoutretrofit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ashmit.mvvmwithroomwithoutretrofit.R
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDAO
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDB
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.QuoteModel
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import com.ashmit.mvvmwithroomwithoutretrofit.databinding.ActivityMainBinding
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel.MainViewModel
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodelfactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)

        //moved to viewmodel cause u cant access repo or data from the view
        val quoteDao : QuoteDAO  = QuoteDB.getDatabase(this).quoteDao()
        val repository = Repository(quoteDao)

        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.getQuotes().observe(this){quoteList->
            binding.quote = quoteList.toString()
        }

        binding.btnAddQuote.setOnClickListener {
            viewModel.insertQuote(QuoteModel(0,"Zindagi aam nhi h dost khass h !!", "Jaggu "))
        }

    }
}