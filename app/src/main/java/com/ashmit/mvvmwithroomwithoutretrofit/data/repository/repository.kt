package com.ashmit.mvvmwithroomwithoutretrofit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ashmit.mvvmwithroomwithoutretrofit.data.db.QuoteDao
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import javax.inject.Inject

//iss constructor mai
// @Provides
//    @Singleton
//    fun providesQuotesDao(quoteDatabase : QuoteDB):QuoteDao{
//        return quoteDatabase.quoteDao() -> ye use ho rha , na ki QuotesDao() -> ye error kuyki ye interface and implement nhi hua h!!
//    }
//na ki jo dao humne banaya h vo call ho rha kuyki vo toh sirf ek interface and uske methods implements hongeyd database.getDao kuyki phely db banega and then room compiler uss dao interface ki implementation generate karega and then we can access it !
class Repository @Inject constructor(private val quoteDao:QuoteDao){

    suspend fun insertQuote(quote :Quote){
        try{
            quoteDao.insertQuote(quote)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getQuotes() : LiveData<List<Quote>>{
        try{
            return quoteDao.getQuotes()
        }catch (e:Exception){
            e.printStackTrace()
//            return MutableLiveData<List<Quote>>().apply { value = emptyList() }
            //or
            return MutableLiveData(emptyList())
        }
    }

    suspend fun updateQuote(quote :Quote) {
        try{
            quoteDao.updateQuote(quote)
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    suspend fun deleteQuote(quote:Quote) {
        try{
            quoteDao.deleteQuote(quote)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun  getQuoteById(id:Int) : LiveData<Quote> {
        try{
            return quoteDao.getQuoteById(id)
        }catch (e:Exception){
            e.printStackTrace()
            return MutableLiveData(Quote(0 , "null","null"))
        }
    }
}