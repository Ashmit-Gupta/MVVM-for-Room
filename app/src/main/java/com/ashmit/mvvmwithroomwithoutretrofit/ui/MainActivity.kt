package com.ashmit.mvvmwithroomwithoutretrofit.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ashmit.mvvmwithroomwithoutretrofit.R
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDB
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import com.ashmit.mvvmwithroomwithoutretrofit.data.repository.Repository
import com.ashmit.mvvmwithroomwithoutretrofit.databinding.ActivityMainBinding
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodel.MainViewModel
import com.ashmit.mvvmwithroomwithoutretrofit.ui.viewmodelfactory.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    @Inject
    lateinit var mainViewModel : MainViewModel
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

//        val quoteDb = QuoteDB.getDatabase(this).quoteDao()
//        val repository = Repository(quoteDb)

//        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.getQuotes().observe(this) { quoteList ->
                binding.quote = quoteList.toString()
        }

        mainViewModel.getQuotes().observe(this) { quoteList ->
            binding.btnGetQuote.setOnClickListener {
                binding.quote = quoteList.toString()
            }
        }

        //not working null !!
        binding.btnGetQuoteById.setOnClickListener {
            mainViewModel.getUserById(1).observe(this){
                binding.quote = mainViewModel.getUserById(1).value.toString()
            }
        }

        binding.btnAddQuote.setOnClickListener {
            mainViewModel.insertQuote(Quote(0 , "hey there i am trying to learn room with mvvm !!" , "Via CHEEZY CODE !1"))
            Toast.makeText(this , "Data Inserted Successfully ", Toast.LENGTH_SHORT).show()
        }
        binding.btnDeleteQuote.setOnClickListener {
            mainViewModel.deleteQuote(Quote(109 , "kuch bhi" ,"nullu"))
        }

        binding
            .btnUpdateQuote.setOnClickListener {
                mainViewModel.updateQuote(Quote(108 , "this is the updated quote !!!" , "via Hacker "))
            }


    }
}