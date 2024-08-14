package com.ashmit.mvvmwithroomwithoutretrofit.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.QuoteModel

@Dao
interface QuoteDAO {
    @Insert
    suspend fun insertQuote(quote: QuoteModel)

//    @Update
//    suspend fun updateQuote()

    @Query("SELECT * FROM quote")
    fun getQuotes(): LiveData<List<QuoteModel>> //marked as LiveData so no need to mark as suspend cause it is returning live data and therefore it will automatically be run in the background thread
}