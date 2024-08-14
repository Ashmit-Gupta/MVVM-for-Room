package com.ashmit.mvvmwithroomwithoutretrofit.data.repository

import androidx.lifecycle.LiveData
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDAO
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.QuoteModel

class Repository(private val quoteDao : QuoteDAO) {
    fun getQuotes():LiveData<List<QuoteModel>>{
        return quoteDao.getQuotes()
    }

    suspend fun insertQuote(quote: QuoteModel){
        return quoteDao.insertQuote(quote)
    }
}